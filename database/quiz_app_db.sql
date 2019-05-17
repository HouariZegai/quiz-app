--
-- Database: java_quiz_db
--

CREATE DATABASE quiz_app_db;

USE quiz_app_db;

-- --------------------------------------------------------

--
-- Structure of table `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `question` varchar(800) NOT NULL,
  `response1` varchar(200) NOT NULL,
  `response2` varchar(200) NOT NULL,
  `response3` varchar(200) NOT NULL,
  `response4` varchar(200) NOT NULL,
  `type` int(11) NOT NULL
);

--
-- Data of table `questions`
--

INSERT INTO `questions` (`question`, `response1`, `response2`, `response3`, `response4`, `type`) VALUES
('What is the out put of this line ?\r\nSystem.out.print(\"Hello\");\r\nSystem.out.println(\"World\");\r\n', 'Hello World', 'HelloWorld', 'hello', 'WorldHello', 1),
('What is the out put of this line ?\r\nSystem.out.print(\"2*3\");', '2*3', '6', '\"2*3\"', '8', 1),
('helhelhle\nhel\nhelh\nhelhelh\nhelhe\nhelhelhle\nhel\nhelh\nhelhelh\nhelhe\nhelhelhle\nhel\nhelh\nhelhelh\nhelhe\nhelhelhle\nhel\nhelh\nhelhelh\nhelhe\nhelhelhle\nhel\nhelh\nhelhelh\nhelhe\nhelhelhle\nhel\nhelh\nhelhelh\nhelhe\n', 'heloo', 'wraptext', 'yes', 'no or other', 1);

