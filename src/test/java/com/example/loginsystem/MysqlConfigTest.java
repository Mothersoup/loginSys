package com.example.loginsystem;


import org.junit.jupiter.api.AfterEach; // 新增：用於測試後清理
import org.junit.jupiter.api.BeforeEach; // 新增：用於測試前準備
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException; // 新增：處理查詢不到結果的情況
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MysqlConfigTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // --- 測試前準備與測試後清理 ---
    @BeforeEach
    void setup() {
        // 1. 確保 test_tables 表存在
        // 這裡使用 IF NOT EXISTS，即使表已存在也不會報錯
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS test_tables (id INT AUTO_INCREMENT PRIMARY KEY)");

        // 2. 清空數據（如果表存在，並且有殘留數據）
        jdbcTemplate.execute("DELETE FROM test_tables");
        // 重置 AUTO_INCREMENT，讓 id 從 1 開始
        jdbcTemplate.execute("ALTER TABLE test_tables AUTO_INCREMENT = 1");
    }

    @AfterEach
    void tearDown() {
        // 在每個測試方法執行後，再次清空 test_tables
        jdbcTemplate.execute("DELETE FROM test_tables");
        jdbcTemplate.execute("ALTER TABLE test_tables AUTO_INCREMENT = 1");
    }

    // --- 連線測試 (基於您的 testMysqlConnection 稍作調整) ---
    @Test
    void testMysqlConnectionAndVersion() {
        try (Connection connection = dataSource.getConnection()) {
            assertFalse(connection.isClosed(), "MySQL 連線應該保持開啟狀態");

            String version = jdbcTemplate.queryForObject("SELECT VERSION()", String.class);
            assertNotNull(version, "應該能獲取 MySQL 版本");
            System.out.println("Connected to MySQL version: " + version);

        } catch (SQLException e) {
            fail("MySQL 連線失敗: " + e.getMessage());
        }
    }

    // --- CREATE (建立) 測試 ---
    @Test
    void testCreateId() {
        // 插入一條記錄，由於 id 是 AUTO_INCREMENT，我們不需要提供 id
        // 語法可以寫成 VALUES() 或 VALUES(NULL)
        int affectedRows = jdbcTemplate.update("INSERT INTO test_tables () VALUES ()");
        assertEquals(1, affectedRows, "應該成功插入一條記錄");

        // 驗證插入的 id 是否是 1
        // 因為每次測試前都重置了 AUTO_INCREMENT
        Integer id = jdbcTemplate.queryForObject("SELECT id FROM test_tables WHERE id = 1", Integer.class);
        assertNotNull(id, "應該能找到剛插入的 ID");
        assertEquals(1, id, "插入的第一條記錄 ID 應該是 1");
    }

    // --- READ (讀取) 測試 ---
    @Test
    void testReadId() {
        // 先插入幾條數據以便讀取
        jdbcTemplate.update("INSERT INTO test_tables () VALUES ()"); // ID 1
        jdbcTemplate.update("INSERT INTO test_tables () VALUES ()"); // ID 2
        jdbcTemplate.update("INSERT INTO test_tables () VALUES ()"); // ID 3

        // 讀取所有 ID
        List<Map<String, Object>> allIds = jdbcTemplate.queryForList("SELECT id FROM test_tables");
        assertEquals(3, allIds.size(), "應該讀取到 3 條記錄");
        assertTrue(allIds.stream().anyMatch(map -> map.get("id").equals(1)), "應該包含 ID 為 1 的記錄");
        assertTrue(allIds.stream().anyMatch(map -> map.get("id").equals(2)), "應該包含 ID 為 2 的記錄");
        assertTrue(allIds.stream().anyMatch(map -> map.get("id").equals(3)), "應該包含 ID 為 3 的記錄");


        // 讀取特定 ID
        Integer foundId = jdbcTemplate.queryForObject("SELECT id FROM test_tables WHERE id = 2", Integer.class);
        assertNotNull(foundId, "應該能找到 ID 為 2 的記錄");
        assertEquals(2, foundId, "找到的 ID 應該是 2");

        // 測試讀取不存在的 ID
        assertThrows(EmptyResultDataAccessException.class, () -> {
            jdbcTemplate.queryForObject("SELECT id FROM test_tables WHERE id = 999", Integer.class);
        }, "查詢不存在的 ID 應該拋出 EmptyResultDataAccessException");
    }

    // --- UPDATE (更新) 測試 ---
    @Test
    void testUpdateId() {
        // 先插入一條數據，以便更新
        jdbcTemplate.update("INSERT INTO test_tables () VALUES ()"); // 插入 ID 1

        // 更新 ID 欄位本身（雖然不常見，但為測試目的可以這麼做）
        // 注意：更新 PRIMARY KEY 通常不是好習慣，這裡只是為了示範語法
        int affectedRows = jdbcTemplate.update("UPDATE test_tables SET id = 100 WHERE id = 1");
        assertEquals(1, affectedRows, "應該成功更新一條記錄");

        // 驗證更新是否成功
        Integer updatedId = jdbcTemplate.queryForObject("SELECT id FROM test_tables WHERE id = 100", Integer.class);
        assertNotNull(updatedId, "應該能找到更新後的 ID");
        assertEquals(100, updatedId, "更新後的 ID 應該是 100");

        // 驗證原來的 ID 是否已不存在
        assertThrows(EmptyResultDataAccessException.class, () -> {
            jdbcTemplate.queryForObject("SELECT id FROM test_tables WHERE id = 1", Integer.class);
        }, "原來的 ID 1 應該已不存在");
    }

    // --- DELETE (刪除) 測試 ---
    @Test
    void testDeleteId() {
        // 先插入幾條數據，以便刪除
        jdbcTemplate.update("INSERT INTO test_tables () VALUES ()"); // ID 1
        jdbcTemplate.update("INSERT INTO test_tables () VALUES ()"); // ID 2
        jdbcTemplate.update("INSERT INTO test_tables () VALUES ()"); // ID 3

        // 驗證 ID 2 是否存在
        assertTrue(jdbcTemplate.queryForList("SELECT id FROM test_tables WHERE id = 2").size() > 0, "刪除前 ID 2 應該存在");

        // 刪除 ID 為 2 的記錄
        int affectedRows = jdbcTemplate.update("DELETE FROM test_tables WHERE id = 2");
        assertEquals(1, affectedRows, "應該成功刪除一條記錄");

        // 驗證 ID 2 是否已被刪除
        assertThrows(EmptyResultDataAccessException.class, () -> {
            jdbcTemplate.queryForObject("SELECT id FROM test_tables WHERE id = 2", Integer.class);
        }, "刪除後的 ID 2 應該查詢不到，拋出 EmptyResultDataAccessException");

        // 驗證其他 ID 是否還存在
        Integer id1 = jdbcTemplate.queryForObject("SELECT id FROM test_tables WHERE id = 1", Integer.class);
        assertNotNull(id1, "ID 1 應該仍然存在");
        Integer id3 = jdbcTemplate.queryForObject("SELECT id FROM test_tables WHERE id = 3", Integer.class);
        assertNotNull(id3, "ID 3 應該仍然存在");
    }
}
