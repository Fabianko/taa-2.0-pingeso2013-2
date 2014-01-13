-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 13-01-2014 a las 22:06:42
-- Versión del servidor: 5.5.24-log
-- Versión de PHP: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `taa`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `assignment`
--

CREATE TABLE IF NOT EXISTS `assignment` (
  `COURSE_CODE` varchar(20) NOT NULL,
  `RUT` varchar(13) NOT NULL,
  `ASSIGNMENT_DATE` datetime DEFAULT NULL,
  `ASSIGNMENT_STATE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`COURSE_CODE`,`RUT`),
  KEY `FK_ASSIGNMENT` (`RUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `assignment`
--

INSERT INTO `assignment` (`COURSE_CODE`, `RUT`, `ASSIGNMENT_DATE`, `ASSIGNMENT_STATE`) VALUES
('PINGESO002', '222222222', NULL, '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `attendance`
--

CREATE TABLE IF NOT EXISTS `attendance` (
  `COURSE_CODE` varchar(20) NOT NULL,
  `RUT` varchar(13) NOT NULL,
  `ATTENDANCE_DATE` datetime NOT NULL,
  `TIMETABLE_CODE` varchar(20) DEFAULT NULL,
  `BLOCK_NUMBER` int(10) unsigned DEFAULT NULL,
  `BLOCK_DAY` varchar(10) DEFAULT NULL,
  `PRESENT` varchar(1) DEFAULT NULL,
  `ATTENDANCE_STATE` varchar(1) DEFAULT NULL,
  `WORKLOAD_BLOCK` varchar(250) DEFAULT NULL,
  `DATE_TEMP` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`COURSE_CODE`,`RUT`,`ATTENDANCE_DATE`),
  KEY `FK_CONTAIN_3` (`TIMETABLE_CODE`,`BLOCK_NUMBER`,`BLOCK_DAY`),
  KEY `FK_HAS_4` (`RUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `attendance`
--

INSERT INTO `attendance` (`COURSE_CODE`, `RUT`, `ATTENDANCE_DATE`, `TIMETABLE_CODE`, `BLOCK_NUMBER`, `BLOCK_DAY`, `PRESENT`, `ATTENDANCE_STATE`, `WORKLOAD_BLOCK`, `DATE_TEMP`) VALUES
('PINGESO002', '160954361', '2014-01-13 18:10:01', NULL, NULL, NULL, '0', '1', 'L3', '13/01/2014'),
('PINGESO002', '160954361', '2014-01-13 18:11:36', NULL, NULL, NULL, '1', '1', 'W4', '13/01/2014'),
('PINGESO002', '160954361', '2014-01-13 18:12:20', NULL, NULL, NULL, '1', '1', 'V5', '13/01/2014'),
('PINGESO002', '62449195', '2014-01-13 18:10:01', NULL, NULL, NULL, '0', '1', 'L3', '13/01/2014'),
('PINGESO002', '62449195', '2014-01-13 18:11:36', NULL, NULL, NULL, '1', '1', 'W4', '13/01/2014'),
('PINGESO002', '62449195', '2014-01-13 18:12:20', NULL, NULL, NULL, '0', '1', 'V5', '13/01/2014');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `block`
--

CREATE TABLE IF NOT EXISTS `block` (
  `TIMETABLE_CODE` varchar(20) NOT NULL,
  `BLOCK_NUMBER` int(10) unsigned NOT NULL,
  `BLOCK_DAY` varchar(10) NOT NULL,
  PRIMARY KEY (`TIMETABLE_CODE`,`BLOCK_NUMBER`,`BLOCK_DAY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coordinator`
--

CREATE TABLE IF NOT EXISTS `coordinator` (
  `RUT` varchar(13) NOT NULL,
  `RUT_UNIVERSITY` varchar(13) DEFAULT NULL,
  PRIMARY KEY (`RUT`),
  KEY `FK_BELONG_TO_5` (`RUT_UNIVERSITY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `COURSE_CODE` varchar(20) NOT NULL,
  `COURSE_NAME` varchar(50) DEFAULT NULL,
  `MAIN_CLASSROOM` varchar(10) DEFAULT NULL,
  `ATTENDANCE_REQUIRED` float DEFAULT NULL,
  `COURSE_STATE` varchar(1) DEFAULT NULL,
  `WORKLOAD` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`COURSE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `course`
--

INSERT INTO `course` (`COURSE_CODE`, `COURSE_NAME`, `MAIN_CLASSROOM`, `ATTENDANCE_REQUIRED`, `COURSE_STATE`, `WORKLOAD`) VALUES
('PINGESO002', 'Ingenieria de software', NULL, 40, '1', 'M2 J2 L3 W3 W4 W5 V5 W6');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `course_history`
--

CREATE TABLE IF NOT EXISTS `course_history` (
  `HISTORY_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `COURSE_CODE` varchar(20) DEFAULT NULL,
  `TIMETABLE_CODE` varchar(20) DEFAULT NULL,
  `BLOCK_NUMBER` int(10) unsigned DEFAULT NULL,
  `BLOCK_DAY` varchar(10) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  `STUDENTS_TOTAL` int(11) DEFAULT NULL,
  `PRESENTS_TOTAL` int(11) DEFAULT NULL,
  `HISTORY_STATE` varchar(1) DEFAULT NULL,
  `WORKLOAD_BLOCK_HISTORY` varchar(250) DEFAULT NULL,
  `DATE_TEMP` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`HISTORY_ID`),
  KEY `FK_CONTAIN` (`TIMETABLE_CODE`,`BLOCK_NUMBER`,`BLOCK_DAY`),
  KEY `FK_HAS_1` (`COURSE_CODE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=63 ;

--
-- Volcado de datos para la tabla `course_history`
--

INSERT INTO `course_history` (`HISTORY_ID`, `COURSE_CODE`, `TIMETABLE_CODE`, `BLOCK_NUMBER`, `BLOCK_DAY`, `DATE`, `STUDENTS_TOTAL`, `PRESENTS_TOTAL`, `HISTORY_STATE`, `WORKLOAD_BLOCK_HISTORY`, `DATE_TEMP`) VALUES
(60, 'PINGESO002', NULL, NULL, NULL, '2014-01-13 18:10:01', 2, 0, '1', 'L3', '13/01/2014'),
(61, 'PINGESO002', NULL, NULL, NULL, '2014-01-13 18:11:36', 2, 2, '1', 'W4', '13/01/2014'),
(62, 'PINGESO002', NULL, NULL, NULL, '2014-01-13 18:12:20', 2, 1, '1', 'V5', '13/01/2014');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `membership`
--

CREATE TABLE IF NOT EXISTS `membership` (
  `COURSE_CODE` varchar(20) NOT NULL,
  `RUT` varchar(13) NOT NULL,
  `MEMBERSHIP_DATE` datetime DEFAULT NULL,
  `MEMBERSHIP_STATE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`COURSE_CODE`,`RUT`),
  KEY `FK_HAS_5` (`RUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `membership`
--

INSERT INTO `membership` (`COURSE_CODE`, `RUT`, `MEMBERSHIP_DATE`, `MEMBERSHIP_STATE`) VALUES
('PINGESO002', '160954361', '2014-01-12 18:29:47', '1'),
('PINGESO002', '62449195', '2014-01-12 01:52:33', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `photo`
--

CREATE TABLE IF NOT EXISTS `photo` (
  `PHOTO_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `RUT` varchar(13) DEFAULT NULL,
  `PHOTO_PATH` varchar(500) DEFAULT NULL,
  `PHOTO_STATE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`PHOTO_ID`),
  KEY `FK_HAS_3` (`RUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `program`
--

CREATE TABLE IF NOT EXISTS `program` (
  `PROGRAM_CODE` varchar(250) NOT NULL,
  `PROGRAM_NAME` varchar(100) NOT NULL,
  `SCHOOL_CODE` varchar(100) DEFAULT NULL,
  `LEVELS` int(11) DEFAULT NULL,
  `PROGRAM_STATE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_CODE`),
  KEY `FK_HAS_12` (`SCHOOL_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `program`
--

INSERT INTO `program` (`PROGRAM_CODE`, `PROGRAM_NAME`, `SCHOOL_CODE`, `LEVELS`, `PROGRAM_STATE`) VALUES
('432567893Facultaddepsicologia11012014041628Ingenieriacivilindustrial11012014050100', 'Ingenieria civil industrial', '432567893Facultaddepsicologia11012014041628', 12, '1'),
('812345117Escueladeartes11012014041707Ingenieriacivilindustrial11012014052948', 'Ingenieria civil industrial', '812345117Escueladeeconomia11012014041636', 12, '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `program_course`
--

CREATE TABLE IF NOT EXISTS `program_course` (
  `COURSE_CODE` varchar(20) NOT NULL,
  `PROGRAM_CODE` varchar(250) NOT NULL,
  PRIMARY KEY (`COURSE_CODE`,`PROGRAM_CODE`),
  KEY `FK_HAS_6` (`PROGRAM_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `school`
--

CREATE TABLE IF NOT EXISTS `school` (
  `SCHOOL_CODE` varchar(100) NOT NULL,
  `RUT_UNIVERSITY` varchar(13) DEFAULT NULL,
  `TIMETABLE_CODE` varchar(20) DEFAULT NULL,
  `SCHOOL_NAME` varchar(50) NOT NULL,
  `SCHOOL_ADRESS` varchar(100) DEFAULT NULL,
  `SCHOOL_PHONE` varchar(30) DEFAULT NULL,
  `SCHOOL_EMAIL` varchar(100) DEFAULT NULL,
  `SCHOOL_STATE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHOOL_CODE`),
  KEY `FK_HAS_11` (`RUT_UNIVERSITY`),
  KEY `FK_HAS_9` (`TIMETABLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `school`
--

INSERT INTO `school` (`SCHOOL_CODE`, `RUT_UNIVERSITY`, `TIMETABLE_CODE`, `SCHOOL_NAME`, `SCHOOL_ADRESS`, `SCHOOL_PHONE`, `SCHOOL_EMAIL`, `SCHOOL_STATE`) VALUES
('432567893Facultaddepsicologia11012014041628', '723451230', NULL, 'Facultad de psicologia', '', '', '', '1'),
('723451230Escueladeartes11012014041602', '723451230', NULL, 'Escuela de artes', '', '', '', '1'),
('723451230Facultaddeingenieria11012014041610', '723451230', NULL, 'Facultad de ingenieria', '', '', '', '1'),
('723451230Facultaddemedicina11012014041616', '432567893', NULL, 'Facultad de medicina', '', '', '', '1'),
('812345117Escueladeartes11012014041707', '812345117', NULL, 'Escuela de artes', '', '', '', '1'),
('812345117Escueladeeconomia11012014041636', '812345117', NULL, 'Escuela de economia', '', '', '', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `RUT` varchar(13) NOT NULL,
  `PROGRAM_CODE` varchar(250) NOT NULL,
  `RUT_UNIVERSITY` varchar(13) DEFAULT NULL,
  PRIMARY KEY (`RUT`),
  KEY `FK_BELONG_TO` (`RUT_UNIVERSITY`),
  KEY `FK_BELONG_TO_3` (`PROGRAM_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `student`
--

INSERT INTO `student` (`RUT`, `PROGRAM_CODE`, `RUT_UNIVERSITY`) VALUES
('160954361', '812345117Escueladeartes11012014041707Ingenieriacivilindustrial11012014052948', '723451230'),
('333333333', '812345117Escueladeartes11012014041707Ingenieriacivilindustrial11012014052948', '432567893'),
('62449195', '432567893Facultaddepsicologia11012014041628Ingenieriacivilindustrial11012014050100', '723451230');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `student_team`
--

CREATE TABLE IF NOT EXISTS `student_team` (
  `TEAM_ID` bigint(20) unsigned NOT NULL,
  `RUT` varchar(13) NOT NULL,
  `ROL_STUDENT` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`TEAM_ID`,`RUT`),
  KEY `FK_HAS_13` (`RUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `student_team`
--

INSERT INTO `student_team` (`TEAM_ID`, `RUT`, `ROL_STUDENT`) VALUES
(15, '160954361', NULL),
(15, '62449195', NULL),
(16, '62449195', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `teacher`
--

CREATE TABLE IF NOT EXISTS `teacher` (
  `RUT` varchar(13) NOT NULL,
  PRIMARY KEY (`RUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `teacher`
--

INSERT INTO `teacher` (`RUT`) VALUES
('222222222');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `team`
--

CREATE TABLE IF NOT EXISTS `team` (
  `TEAM_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `TEAM_NAME` varchar(50) DEFAULT NULL,
  `COURSE_CODE` varchar(20) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `TEAM_STATE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`TEAM_ID`),
  KEY `FK_BELONG_TO_4` (`COURSE_CODE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Volcado de datos para la tabla `team`
--

INSERT INTO `team` (`TEAM_ID`, `TEAM_NAME`, `COURSE_CODE`, `CREATION_DATE`, `TEAM_STATE`) VALUES
(15, 'Java4Ever', 'PINGESO002', '2014-01-13 08:30:55', '1'),
(16, 'jhghg', 'PINGESO002', '2014-01-13 18:14:53', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `timetable`
--

CREATE TABLE IF NOT EXISTS `timetable` (
  `TIMETABLE_CODE` varchar(20) NOT NULL,
  `TIMETABLE_NAME` varchar(50) NOT NULL,
  `BLOCKS_NUMBER_TOTAL` int(10) unsigned NOT NULL,
  `BLOCK_DURATION` int(10) unsigned NOT NULL,
  `BEGIN_FIRST_BLOCK` time NOT NULL,
  `END_LAST_BLOCK` time NOT NULL,
  `TIMETABLE_STATE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`TIMETABLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `timetable`
--

INSERT INTO `timetable` (`TIMETABLE_CODE`, `TIMETABLE_NAME`, `BLOCKS_NUMBER_TOTAL`, `BLOCK_DURATION`, `BEGIN_FIRST_BLOCK`, `END_LAST_BLOCK`, `TIMETABLE_STATE`) VALUES
('USACH001', 'Horario Diurno Ingenieria USACH', 15, 45, '08:00:00', '22:00:00', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `university`
--

CREATE TABLE IF NOT EXISTS `university` (
  `RUT_UNIVERSITY` varchar(13) NOT NULL,
  `UNIVERSITY_NAME` varchar(50) NOT NULL,
  `UNIVERSITY_HEADQUARTER_ADRESS` varchar(100) DEFAULT NULL,
  `UNIVERSITY_HEADQUARTER_PHONE` varchar(30) DEFAULT NULL,
  `UNIVERSITY_HEADQUARTER_EMAIL` varchar(100) DEFAULT NULL,
  `UNIVERSITY_STATE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`RUT_UNIVERSITY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `university`
--

INSERT INTO `university` (`RUT_UNIVERSITY`, `UNIVERSITY_NAME`, `UNIVERSITY_HEADQUARTER_ADRESS`, `UNIVERSITY_HEADQUARTER_PHONE`, `UNIVERSITY_HEADQUARTER_EMAIL`, `UNIVERSITY_STATE`) VALUES
('432567893', 'Universidad Bolivariana', 'Estado 1540, Santiago', '', '', '1'),
('723451230', 'Universidad de Santiago (USACH)', 'Alameda 143 Estacion Central', '(02) 537 67 89', 'info@usach.cl', '1'),
('812345117', 'Universidad Iberoamericana (UNICIT)', 'Dieciocho 4550', '(02) 471 23 92', 'info@unicit.cl', '1'),
('931678957', 'Universidad de Las Americas (UDLA)', '', '', '', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `RUT` varchar(13) NOT NULL,
  `FIRST_NAME` varchar(50) DEFAULT NULL,
  `MIDDLE_NAME` varchar(50) DEFAULT NULL,
  `PRIMARY_LAST_NAME` varchar(50) DEFAULT NULL,
  `SECOND_LAST_NAME` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(32) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `USER_STATE` varchar(1) DEFAULT NULL,
  `ROL` varchar(20) NOT NULL,
  PRIMARY KEY (`RUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`RUT`, `FIRST_NAME`, `MIDDLE_NAME`, `PRIMARY_LAST_NAME`, `SECOND_LAST_NAME`, `PASSWORD`, `EMAIL`, `USER_STATE`, `ROL`) VALUES
('160954361', 'Diego', NULL, 'Garcia', 'Melo', '202cb962ac59075b964b07152d234b70', 'diego.garcia@usach.cl', '1', 'Estudiante'),
('222222222', 'Juanito', '', 'Perez', 'Perez', 'd777e9e30b918e9034ab610712c90cf', 'info@usach.cl', '1', 'Profesor'),
('333333333', 'Luis', '', 'Mario', 'Leiva', NULL, 'info@unicit.cl', '1', 'Estudiante'),
('62449195', 'Sylvia', '', 'Melo', 'Gecele', NULL, 'info@usach.cl', '1', 'Estudiante');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `workload`
--

CREATE TABLE IF NOT EXISTS `workload` (
  `TIMETABLE_CODE` varchar(20) NOT NULL,
  `BLOCK_NUMBER` int(10) unsigned NOT NULL,
  `BLOCK_DAY` varchar(10) NOT NULL,
  `COURSE_CODE` varchar(20) NOT NULL,
  PRIMARY KEY (`TIMETABLE_CODE`,`BLOCK_NUMBER`,`BLOCK_DAY`,`COURSE_CODE`),
  KEY `FK_HAS_8` (`COURSE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `assignment`
--
ALTER TABLE `assignment`
  ADD CONSTRAINT `FK_ASSIGNMENT` FOREIGN KEY (`RUT`) REFERENCES `teacher` (`RUT`),
  ADD CONSTRAINT `FK_ASSIGNMENT_2` FOREIGN KEY (`COURSE_CODE`) REFERENCES `course` (`COURSE_CODE`);

--
-- Filtros para la tabla `attendance`
--
ALTER TABLE `attendance`
  ADD CONSTRAINT `FK_CONTAIN_3` FOREIGN KEY (`TIMETABLE_CODE`, `BLOCK_NUMBER`, `BLOCK_DAY`) REFERENCES `block` (`TIMETABLE_CODE`, `BLOCK_NUMBER`, `BLOCK_DAY`),
  ADD CONSTRAINT `FK_HAS_2` FOREIGN KEY (`COURSE_CODE`) REFERENCES `course` (`COURSE_CODE`),
  ADD CONSTRAINT `FK_HAS_4` FOREIGN KEY (`RUT`) REFERENCES `student` (`RUT`);

--
-- Filtros para la tabla `block`
--
ALTER TABLE `block`
  ADD CONSTRAINT `FK_HAS_10` FOREIGN KEY (`TIMETABLE_CODE`) REFERENCES `timetable` (`TIMETABLE_CODE`);

--
-- Filtros para la tabla `coordinator`
--
ALTER TABLE `coordinator`
  ADD CONSTRAINT `FK_BELONG_TO_5` FOREIGN KEY (`RUT_UNIVERSITY`) REFERENCES `university` (`RUT_UNIVERSITY`),
  ADD CONSTRAINT `FK_MIGHT_BE_COORDINATOR` FOREIGN KEY (`RUT`) REFERENCES `user` (`RUT`);

--
-- Filtros para la tabla `course_history`
--
ALTER TABLE `course_history`
  ADD CONSTRAINT `FK_CONTAIN` FOREIGN KEY (`TIMETABLE_CODE`, `BLOCK_NUMBER`, `BLOCK_DAY`) REFERENCES `block` (`TIMETABLE_CODE`, `BLOCK_NUMBER`, `BLOCK_DAY`),
  ADD CONSTRAINT `FK_HAS_1` FOREIGN KEY (`COURSE_CODE`) REFERENCES `course` (`COURSE_CODE`);

--
-- Filtros para la tabla `membership`
--
ALTER TABLE `membership`
  ADD CONSTRAINT `FK_BELONG_TO_2` FOREIGN KEY (`COURSE_CODE`) REFERENCES `course` (`COURSE_CODE`),
  ADD CONSTRAINT `FK_HAS_5` FOREIGN KEY (`RUT`) REFERENCES `student` (`RUT`);

--
-- Filtros para la tabla `photo`
--
ALTER TABLE `photo`
  ADD CONSTRAINT `FK_HAS_3` FOREIGN KEY (`RUT`) REFERENCES `student` (`RUT`);

--
-- Filtros para la tabla `program`
--
ALTER TABLE `program`
  ADD CONSTRAINT `FK_HAS_12` FOREIGN KEY (`SCHOOL_CODE`) REFERENCES `school` (`SCHOOL_CODE`);

--
-- Filtros para la tabla `program_course`
--
ALTER TABLE `program_course`
  ADD CONSTRAINT `FK_HAS_6` FOREIGN KEY (`PROGRAM_CODE`) REFERENCES `program` (`PROGRAM_CODE`),
  ADD CONSTRAINT `FK_HAS_7` FOREIGN KEY (`COURSE_CODE`) REFERENCES `course` (`COURSE_CODE`);

--
-- Filtros para la tabla `school`
--
ALTER TABLE `school`
  ADD CONSTRAINT `FK_HAS_11` FOREIGN KEY (`RUT_UNIVERSITY`) REFERENCES `university` (`RUT_UNIVERSITY`),
  ADD CONSTRAINT `FK_HAS_9` FOREIGN KEY (`TIMETABLE_CODE`) REFERENCES `timetable` (`TIMETABLE_CODE`);

--
-- Filtros para la tabla `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `FK_BELONG_TO` FOREIGN KEY (`RUT_UNIVERSITY`) REFERENCES `university` (`RUT_UNIVERSITY`),
  ADD CONSTRAINT `FK_BELONG_TO_3` FOREIGN KEY (`PROGRAM_CODE`) REFERENCES `program` (`PROGRAM_CODE`),
  ADD CONSTRAINT `FK_MIGHT_BE_STUDENT` FOREIGN KEY (`RUT`) REFERENCES `user` (`RUT`);

--
-- Filtros para la tabla `student_team`
--
ALTER TABLE `student_team`
  ADD CONSTRAINT `FK_HAS_13` FOREIGN KEY (`RUT`) REFERENCES `student` (`RUT`),
  ADD CONSTRAINT `FK_HAS_14` FOREIGN KEY (`TEAM_ID`) REFERENCES `team` (`TEAM_ID`);

--
-- Filtros para la tabla `teacher`
--
ALTER TABLE `teacher`
  ADD CONSTRAINT `FK_MIGHT_BE_TEACHER` FOREIGN KEY (`RUT`) REFERENCES `user` (`RUT`);

--
-- Filtros para la tabla `team`
--
ALTER TABLE `team`
  ADD CONSTRAINT `FK_BELONG_TO_4` FOREIGN KEY (`COURSE_CODE`) REFERENCES `course` (`COURSE_CODE`);

--
-- Filtros para la tabla `workload`
--
ALTER TABLE `workload`
  ADD CONSTRAINT `FK_CONTAIN_2` FOREIGN KEY (`TIMETABLE_CODE`, `BLOCK_NUMBER`, `BLOCK_DAY`) REFERENCES `block` (`TIMETABLE_CODE`, `BLOCK_NUMBER`, `BLOCK_DAY`),
  ADD CONSTRAINT `FK_HAS_8` FOREIGN KEY (`COURSE_CODE`) REFERENCES `course` (`COURSE_CODE`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
