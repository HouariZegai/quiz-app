--
-- Base de données :  `java_quiz_db`
--

CREATE DATABASE java_quiz_db;

USE java_quiz_db;

-- --------------------------------------------------------

--
-- Structure de la table `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `question` varchar(800) NOT NULL,
  `response1` varchar(200) NOT NULL,
  `response2` varchar(200) NOT NULL,
  `response3` varchar(200) NOT NULL,
  `response4` varchar(200) NOT NULL,
  `type` int(11) NOT NULL
);

--
-- Contenu de la table `questions`
--

INSERT INTO `questions` (`id`, `question`, `response1`, `response2`, `response3`, `response4`, `type`) VALUES
(1, 'What is the out put of this line ?\r\nSystem.out.print(\"Hello\");\r\nSystem.out.println(\"World\");\r\n', 'Hello World', 'HelloWorld', 'hello', 'WorldHello', 1),
(2, 'What is the out put of this line ?\r\nSystem.out.print(\"2*3\");', '2*3', '6', '\"2*3\"', '8', 1),
(3, 'helhelhle\nhel\nhelh\nhelhelh\nhelhe\nhelhelhle\nhel\nhelh\nhelhelh\nhelhe\nhelhelhle\nhel\nhelh\nhelhelh\nhelhe\nhelhelhle\nhel\nhelh\nhelhelh\nhelhe\nhelhelhle\nhel\nhelh\nhelhelh\nhelhe\nhelhelhle\nhel\nhelh\nhelhelh\nhelhe\n', 'heloo', 'wraptext', 'yes', 'no or other', 1);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;