/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     09/01/2014 5:40:11                           */
/*==============================================================*/


drop table if exists ASSIGNMENT;

drop table if exists ATTENDANCE;

drop table if exists BLOCK;

drop table if exists COORDINATOR;

drop table if exists COURSE;

drop table if exists COURSE_HISTORY;

drop table if exists MEMBERSHIP;

drop table if exists PHOTO;

drop table if exists PROGRAM;

drop table if exists PROGRAM_COURSE;

drop table if exists SCHOOL;

drop table if exists STUDENT;

drop table if exists STUDENT_TEAM;

drop table if exists TEACHER;

drop table if exists TEAM;

drop table if exists TIMETABLE;

drop table if exists UNIVERSITY;

drop table if exists USER;

drop table if exists WORKLOAD;

/*==============================================================*/
/* Table: ASSIGNMENT                                            */
/*==============================================================*/
create table ASSIGNMENT
(
   COURSE_CODE          varchar(20) not null,
   RUT                  varchar(13) not null,
   ASSIGNMENT_DATE      datetime,
   ASSIGNMENT_STATE     varchar(1),
   primary key (COURSE_CODE, RUT)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: ATTENDANCE                                            */
/*==============================================================*/
create table ATTENDANCE
(
   COURSE_CODE          varchar(20) not null,
   RUT                  varchar(13) not null,
   ATTENDANCE_DATE      datetime not null,
   TIMETABLE_CODE       varchar(20),
   BLOCK_NUMBER         int unsigned,
   BLOCK_DAY            varchar(10),
   PRESENT              varchar(1),
   ATTENDANCE_STATE     varchar(1),
   primary key (COURSE_CODE, RUT, ATTENDANCE_DATE)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: BLOCK                                                 */
/*==============================================================*/
create table BLOCK
(
   TIMETABLE_CODE       varchar(20) not null,
   BLOCK_NUMBER         int unsigned not null,
   BLOCK_DAY            varchar(10) not null,
   primary key (TIMETABLE_CODE, BLOCK_NUMBER, BLOCK_DAY)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: COORDINATOR                                           */
/*==============================================================*/
create table COORDINATOR
(
   RUT                  varchar(13) not null,
   RUT_UNIVERSITY       varchar(13),
   primary key (RUT)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: COURSE                                                */
/*==============================================================*/
create table COURSE
(
   COURSE_CODE          varchar(20) not null,
   COURSE_NAME          varchar(50),
   MAIN_CLASSROOM       varchar(10),
   ATTENDANCE_REQUIRED  float,
   COURSE_STATE         varchar(1),
   primary key (COURSE_CODE)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: COURSE_HISTORY                                        */
/*==============================================================*/
create table COURSE_HISTORY
(
   HISTORY_ID           bigint unsigned not null auto_increment,
   COURSE_CODE          varchar(20),
   TIMETABLE_CODE       varchar(20),
   BLOCK_NUMBER         int unsigned,
   BLOCK_DAY            varchar(10),
   DATE                 datetime,
   STUDENTS_TOTAL       int,
   PRESENTS_TOTAL       int,
   HISTORY_STATE        varchar(1),
   primary key (HISTORY_ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: MEMBERSHIP                                            */
/*==============================================================*/
create table MEMBERSHIP
(
   COURSE_CODE          varchar(20) not null,
   RUT                  varchar(13) not null,
   MEMBERSHIP_DATE      datetime,
   MEMBERSHIP_STATE     varchar(1),
   primary key (COURSE_CODE, RUT)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: PHOTO                                                 */
/*==============================================================*/
create table PHOTO
(
   PHOTO_ID             bigint unsigned not null auto_increment,
   RUT                  varchar(13),
   PHOTO_PATH           varchar(500),
   PHOTO_STATE          varchar(1),
   primary key (PHOTO_ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: PROGRAM                                               */
/*==============================================================*/
create table PROGRAM
(
   PROGRAM_CODE         varchar(20) not null,
   PROGRAM_NAME         varchar(100) not null,
   SCHOOL_CODE          varchar(100),
   LEVELS               int,
   PROGRAM_STATE        varchar(1),
   primary key (PROGRAM_CODE)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: PROGRAM_COURSE                                        */
/*==============================================================*/
create table PROGRAM_COURSE
(
   COURSE_CODE          varchar(20) not null,
   PROGRAM_CODE         varchar(20) not null,
   primary key (COURSE_CODE, PROGRAM_CODE)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: SCHOOL                                                */
/*==============================================================*/
create table SCHOOL
(
   SCHOOL_CODE          varchar(100) not null,
   RUT_UNIVERSITY       varchar(13),
   TIMETABLE_CODE       varchar(20),
   SCHOOL_NAME          varchar(50) not null,
   SCHOOL_ADRESS        varchar(100),
   SCHOOL_PHONE         varchar(30),
   SCHOOL_EMAIL         varchar(100),
   SCHOOL_STATE         varchar(1),
   primary key (SCHOOL_CODE)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: STUDENT                                               */
/*==============================================================*/
create table STUDENT
(
   RUT                  varchar(13) not null,
   PROGRAM_CODE         varchar(20) not null,
   RUT_UNIVERSITY       varchar(13),
   primary key (RUT)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: STUDENT_TEAM                                          */
/*==============================================================*/
create table STUDENT_TEAM
(
   TEAM_ID              bigint unsigned not null,
   RUT                  varchar(13) not null,
   ROL_STUDENT          varchar(50) not null,
   primary key (TEAM_ID, RUT)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TEACHER                                               */
/*==============================================================*/
create table TEACHER
(
   RUT                  varchar(13) not null,
   primary key (RUT)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TEAM                                                  */
/*==============================================================*/
create table TEAM
(
   TEAM_ID              bigint unsigned not null auto_increment,
   TEAM_NAME            varchar(50),
   COURSE_CODE          varchar(20),
   CREATION_DATE        datetime,
   TEAM_STATE           varchar(1),
   primary key (TEAM_ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TIMETABLE                                             */
/*==============================================================*/
create table TIMETABLE
(
   TIMETABLE_CODE       varchar(20) not null,
   TIMETABLE_NAME       varchar(50) not null,
   BLOCKS_NUMBER_TOTAL  int unsigned not null,
   BLOCK_DURATION       int unsigned not null,
   BEGIN_FIRST_BLOCK    time not null,
   END_LAST_BLOCK       time not null,
   TIMETABLE_STATE      varchar(1),
   primary key (TIMETABLE_CODE)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: UNIVERSITY                                            */
/*==============================================================*/
create table UNIVERSITY
(
   RUT_UNIVERSITY       varchar(13) not null,
   UNIVERSITY_NAME      varchar(50) not null,
   UNIVERSITY_HEADQUARTER_ADRESS varchar(100),
   UNIVERSITY_HEADQUARTER_PHONE varchar(30),
   UNIVERSITY_HEADQUARTER_EMAIL varchar(100),
   UNIVERSITY_STATE     varchar(1),
   primary key (RUT_UNIVERSITY)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table USER
(
   RUT                  varchar(13) not null,
   FIRST_NAME           varchar(50),
   MIDDLE_NAME          varchar(50),
   PRIMARY_LAST_NAME    varchar(50),
   SECOND_LAST_NAME     varchar(50),
   PASSWORD             varchar(32),
   EMAIL                varchar(100),
   USER_STATE           varchar(1),
   ROL                  varchar(20) not null,
   primary key (RUT)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: WORKLOAD                                              */
/*==============================================================*/
create table WORKLOAD
(
   TIMETABLE_CODE       varchar(20) not null,
   BLOCK_NUMBER         int unsigned not null,
   BLOCK_DAY            varchar(10) not null,
   COURSE_CODE          varchar(20) not null,
   primary key (TIMETABLE_CODE, BLOCK_NUMBER, BLOCK_DAY, COURSE_CODE)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table ASSIGNMENT add constraint FK_ASSIGNMENT foreign key (RUT)
      references TEACHER (RUT) on delete restrict on update restrict;

alter table ASSIGNMENT add constraint FK_ASSIGNMENT_2 foreign key (COURSE_CODE)
      references COURSE (COURSE_CODE) on delete restrict on update restrict;

alter table ATTENDANCE add constraint FK_CONTAIN_3 foreign key (TIMETABLE_CODE, BLOCK_NUMBER, BLOCK_DAY)
      references BLOCK (TIMETABLE_CODE, BLOCK_NUMBER, BLOCK_DAY) on delete restrict on update restrict;

alter table ATTENDANCE add constraint FK_HAS_2 foreign key (COURSE_CODE)
      references COURSE (COURSE_CODE) on delete restrict on update restrict;

alter table ATTENDANCE add constraint FK_HAS_4 foreign key (RUT)
      references STUDENT (RUT) on delete restrict on update restrict;

alter table BLOCK add constraint FK_HAS_10 foreign key (TIMETABLE_CODE)
      references TIMETABLE (TIMETABLE_CODE) on delete restrict on update restrict;

alter table COORDINATOR add constraint FK_BELONG_TO_5 foreign key (RUT_UNIVERSITY)
      references UNIVERSITY (RUT_UNIVERSITY) on delete restrict on update restrict;

alter table COORDINATOR add constraint FK_MIGHT_BE_COORDINATOR foreign key (RUT)
      references USER (RUT) on delete restrict on update restrict;

alter table COURSE_HISTORY add constraint FK_CONTAIN foreign key (TIMETABLE_CODE, BLOCK_NUMBER, BLOCK_DAY)
      references BLOCK (TIMETABLE_CODE, BLOCK_NUMBER, BLOCK_DAY) on delete restrict on update restrict;

alter table COURSE_HISTORY add constraint FK_HAS_1 foreign key (COURSE_CODE)
      references COURSE (COURSE_CODE) on delete restrict on update restrict;

alter table MEMBERSHIP add constraint FK_BELONG_TO_2 foreign key (COURSE_CODE)
      references COURSE (COURSE_CODE) on delete restrict on update restrict;

alter table MEMBERSHIP add constraint FK_HAS_5 foreign key (RUT)
      references STUDENT (RUT) on delete restrict on update restrict;

alter table PHOTO add constraint FK_HAS_3 foreign key (RUT)
      references STUDENT (RUT) on delete restrict on update restrict;

alter table PROGRAM add constraint FK_HAS_12 foreign key (SCHOOL_CODE)
      references SCHOOL (SCHOOL_CODE) on delete restrict on update restrict;

alter table PROGRAM_COURSE add constraint FK_HAS_6 foreign key (PROGRAM_CODE)
      references PROGRAM (PROGRAM_CODE) on delete restrict on update restrict;

alter table PROGRAM_COURSE add constraint FK_HAS_7 foreign key (COURSE_CODE)
      references COURSE (COURSE_CODE) on delete restrict on update restrict;

alter table SCHOOL add constraint FK_HAS_11 foreign key (RUT_UNIVERSITY)
      references UNIVERSITY (RUT_UNIVERSITY) on delete restrict on update restrict;

alter table SCHOOL add constraint FK_HAS_9 foreign key (TIMETABLE_CODE)
      references TIMETABLE (TIMETABLE_CODE) on delete restrict on update restrict;

alter table STUDENT add constraint FK_BELONG_TO foreign key (RUT_UNIVERSITY)
      references UNIVERSITY (RUT_UNIVERSITY) on delete restrict on update restrict;

alter table STUDENT add constraint FK_BELONG_TO_3 foreign key (PROGRAM_CODE)
      references PROGRAM (PROGRAM_CODE) on delete restrict on update restrict;

alter table STUDENT add constraint FK_MIGHT_BE_STUDENT foreign key (RUT)
      references USER (RUT) on delete restrict on update restrict;

alter table STUDENT_TEAM add constraint FK_HAS_13 foreign key (RUT)
      references STUDENT (RUT) on delete restrict on update restrict;

alter table STUDENT_TEAM add constraint FK_HAS_14 foreign key (TEAM_ID)
      references TEAM (TEAM_ID) on delete restrict on update restrict;

alter table TEACHER add constraint FK_MIGHT_BE_TEACHER foreign key (RUT)
      references USER (RUT) on delete restrict on update restrict;

alter table TEAM add constraint FK_BELONG_TO_4 foreign key (COURSE_CODE)
      references COURSE (COURSE_CODE) on delete restrict on update restrict;

alter table WORKLOAD add constraint FK_CONTAIN_2 foreign key (TIMETABLE_CODE, BLOCK_NUMBER, BLOCK_DAY)
      references BLOCK (TIMETABLE_CODE, BLOCK_NUMBER, BLOCK_DAY) on delete restrict on update restrict;

alter table WORKLOAD add constraint FK_HAS_8 foreign key (COURSE_CODE)
      references COURSE (COURSE_CODE) on delete restrict on update restrict;

