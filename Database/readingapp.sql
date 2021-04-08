-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Počítač: 127.0.0.1
-- Vytvořeno: Stř 07. dub 2021, 15:48
-- Verze serveru: 10.4.17-MariaDB
-- Verze PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáze: `readingapp`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `odznakuzivatel`
--

CREATE TABLE `odznakuzivatel` (
  `user_id` int(5) NOT NULL,
  `odznak_id` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `odznakuzivatel`
--

INSERT INTO `odznakuzivatel` (`user_id`, `odznak_id`) VALUES
(1, 0),
(1, 1),
(1, 2),
(1, 3),
(6, 0),
(6, 2),
(6, 3),
(6, 1);

-- --------------------------------------------------------

--
-- Struktura tabulky `plany`
--

CREATE TABLE `plany` (
  `plan_id` int(5) NOT NULL,
  `nazev` varchar(20) NOT NULL,
  `skupina_id` int(5) NOT NULL,
  `strany` int(4) NOT NULL,
  `doba` time NOT NULL,
  `datum_zadani` date DEFAULT NULL,
  `popis` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `plany`
--

INSERT INTO `plany` (`plan_id`, `nazev`, `skupina_id`, `strany`, `doba`, `datum_zadani`, `popis`) VALUES
(1, 'Plan1', 1, 60, '00:45:00', NULL, ''),
(2, 'Plan2', 1, 55, '02:00:00', NULL, ''),
(3, '2021-04-06', 0, 0, '00:00:00', NULL, ''),
(4, 'nazev', 1, 21, '00:43:00', '0000-00-00', 'precti si to');

-- --------------------------------------------------------

--
-- Struktura tabulky `progress`
--

CREATE TABLE `progress` (
  `user_id` int(5) NOT NULL,
  `plan_id` int(5) NOT NULL,
  `strany` int(4) NOT NULL,
  `doba` time NOT NULL,
  `datum_zapisu` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `progress`
--

INSERT INTO `progress` (`user_id`, `plan_id`, `strany`, `doba`, `datum_zapisu`) VALUES
(3, 1, 20, '01:25:00', NULL);

-- --------------------------------------------------------

--
-- Struktura tabulky `skupinauzivatel`
--

CREATE TABLE `skupinauzivatel` (
  `user_id` int(5) NOT NULL,
  `skupina_id` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `skupinauzivatel`
--

INSERT INTO `skupinauzivatel` (`user_id`, `skupina_id`) VALUES
(1, 1),
(3, 1),
(6, 1),
(6, 27),
(6, 28),
(6, 29),
(6, 30),
(6, 31),
(6, 32),
(6, 33),
(7, 33),
(1, 33),
(7, 34),
(10, 1);

-- --------------------------------------------------------

--
-- Struktura tabulky `skupiny`
--

CREATE TABLE `skupiny` (
  `skupina_id` int(5) NOT NULL,
  `nazev` varchar(20) NOT NULL,
  `ucitel_id` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `skupiny`
--

INSERT INTO `skupiny` (`skupina_id`, `nazev`, `ucitel_id`) VALUES
(1, 'SkupinaTest1', 2),
(26, 'pes', 0),
(27, 'pes', 0),
(28, 'nova skupina', 0),
(29, 'nova skupina', 0),
(30, 'nova skupina', 0),
(31, 'nova skupina', 0),
(32, 'nova skupina', 0),
(33, 'Dobrá skupina', 0),
(34, 'lepsí skupina', 5);

-- --------------------------------------------------------

--
-- Struktura tabulky `teachers`
--

CREATE TABLE `teachers` (
  `teacher_id` int(5) NOT NULL,
  `jmeno` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `heslo` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `teachers`
--

INSERT INTO `teachers` (`teacher_id`, `jmeno`, `email`, `heslo`) VALUES
(2, 'kocour', 'k@eca.cz', '123'),
(3, 'ahoj', 'jah', '$2y$10$TpXGIVPZEmclRPpiZ2RdpeHY8EfEjrQHMu7FvYmhT2952TBGAPEA2'),
(4, 'ahojo', 'jahi', '$2y$10$Vv35pmuW65sktyaFRvsg2.pQaTDRQLWiBNDFefhyaabVOlVg6Hai.'),
(5, 'ucite', 'ano', '$2y$10$W4J4lthMAyqIMJxbGi.5ZuNeyaVo5TyMC2exXlFvmUyno2O2K9NdG'),
(6, 'test', 'ahoj', '$2y$10$8B13IAc81bFaLhLDmFtyNeviFcFM4WvTmTfOuyVO.e6JMW6SUV4Vi');

-- --------------------------------------------------------

--
-- Struktura tabulky `users`
--

CREATE TABLE `users` (
  `user_id` int(5) NOT NULL,
  `jmeno` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL,
  `heslo` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `users`
--

INSERT INTO `users` (`user_id`, `jmeno`, `email`, `heslo`) VALUES
(1, 'UserTest1', 'aa@example.com', '123'),
(3, 'UserTest2', 'bb@example.com', '456'),
(6, 'ahoj', 'pes', '123'),
(7, 'hoj', 'kocka', '1234'),
(8, 'John', 'Doe', 'john@example.com'),
(9, 'kocour', 'k@eca.cz', '123'),
(10, 'mistr', 'asd', '$2y$10$nrNNzZcz1ZAsDwSdr4Rzp.fQp2/INhLnfcxV.76YP002fSPi.Mn5a');

-- --------------------------------------------------------

--
-- Struktura tabulky `validloginsstudent`
--

CREATE TABLE `validloginsstudent` (
  `user_id` int(5) NOT NULL,
  `random_number` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `validloginsstudent`
--

INSERT INTO `validloginsstudent` (`user_id`, `random_number`) VALUES
(10, 1126077802);

-- --------------------------------------------------------

--
-- Struktura tabulky `validloginsteacher`
--

CREATE TABLE `validloginsteacher` (
  `user_id` int(5) NOT NULL,
  `random_number` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `validloginsteacher`
--

INSERT INTO `validloginsteacher` (`user_id`, `random_number`) VALUES
(4, 145410359),
(5, 501356417),
(6, 305331265),
(6, 1291571002),
(6, 203115749);

--
-- Klíče pro exportované tabulky
--

--
-- Klíče pro tabulku `plany`
--
ALTER TABLE `plany`
  ADD PRIMARY KEY (`plan_id`);

--
-- Klíče pro tabulku `skupiny`
--
ALTER TABLE `skupiny`
  ADD PRIMARY KEY (`skupina_id`);

--
-- Klíče pro tabulku `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`teacher_id`);

--
-- Klíče pro tabulku `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT pro tabulky
--

--
-- AUTO_INCREMENT pro tabulku `plany`
--
ALTER TABLE `plany`
  MODIFY `plan_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pro tabulku `skupiny`
--
ALTER TABLE `skupiny`
  MODIFY `skupina_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT pro tabulku `teachers`
--
ALTER TABLE `teachers`
  MODIFY `teacher_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pro tabulku `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
