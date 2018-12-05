DROP TABLE IF EXISTS DAYS;


CREATE TABLE DAYS
  ( 
     id          INT PRIMARY KEY, 
     date_yyyymmdd VARCHAR(8), 
     is_holiday     BOOLEAN, 
     is_work_at_home    BOOLEAN, 
     is_birthday     BOOLEAN, 
     is_standby     BOOLEAN,
     is_workday     BOOLEAN
  );