This project is trying to simulating a attendance system in ChungYung University ，you could use qrcode to  take attendance in class.
this project utilizes docker, spring JPA, spring boot, mariadb, redis(not implement yet), mybatis(main focus on JPA though).

there are 4 Roles in User
1. SuperAdmin 
2. Admin
3. Teacher
4. student

this System use JWT implement authorize and varify account role and information.
if JWT is valid and paired with signKey, it would go through username and password filter but no authenticate.
--------------------------------------------------------------------------------------
system operation

SuperAdmin is a  origin account from this system, it would automatically create when application starting but not found any SuperAdmin in database, it would create a SuperAdmin account automatically.

Admin account would be registered by register Admin( only SuperAdmin could do that).

Admin could register a teacher by /register/teacher , (email is required) then system would automatically mail a teacher code to provided email.

Teacher and student registeration should go through the normal way, using /register/up url. teacher registeration also need a teacher code ，that code was provide by receiving mail from system.

-------------------------------------------------------------------------------------
