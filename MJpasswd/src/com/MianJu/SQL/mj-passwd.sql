-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2021-04-27 18:43:29
-- 服务器版本： 5.7.33-log
-- PHP Version: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mj-passwd`
--

-- --------------------------------------------------------

--
-- 表的结构 `passwddate`
--

CREATE TABLE IF NOT EXISTS `passwddate` (
  `p_autoid` int(5) NOT NULL COMMENT '序号',
  `p_order` int(3) NOT NULL DEFAULT '1' COMMENT '用户显示序号',
  `u_id` varchar(10) NOT NULL COMMENT '用户ID',
  `p_zhanghao` varchar(20) NOT NULL COMMENT '用户存储账号',
  `p_mima` varchar(150) NOT NULL COMMENT '用户存储密码',
  `p_shuyu` varchar(20) DEFAULT '空' COMMENT '用户存储属于网站',
  `p_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建数据时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `userdate`
--

CREATE TABLE IF NOT EXISTS `userdate` (
  `u_autoid` int(5) NOT NULL COMMENT '序号',
  `u_id` varchar(10) NOT NULL COMMENT '用户ID',
  `u_name` varchar(15) NOT NULL COMMENT '用户账号昵称',
  `u_passwd` varchar(15) NOT NULL COMMENT '用户密码',
  `u_count` int(3) NOT NULL DEFAULT '0' COMMENT '用户拥有数据数量',
  `u_maxcount` int(8) NOT NULL DEFAULT '10' COMMENT '用户拥有的最大数据数量',
  `u_key` varchar(150) NOT NULL COMMENT '用户密钥',
  `u_email` varchar(30) NOT NULL COMMENT '用户邮箱',
  `u_createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `passwddate`
--
ALTER TABLE `passwddate`
  ADD PRIMARY KEY (`p_autoid`);

--
-- Indexes for table `userdate`
--
ALTER TABLE `userdate`
  ADD PRIMARY KEY (`u_autoid`),
  ADD UNIQUE KEY `u_name` (`u_name`),
  ADD UNIQUE KEY `u_id` (`u_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `passwddate`
--
ALTER TABLE `passwddate`
  MODIFY `p_autoid` int(5) NOT NULL AUTO_INCREMENT COMMENT '序号';
--
-- AUTO_INCREMENT for table `userdate`
--
ALTER TABLE `userdate`
  MODIFY `u_autoid` int(5) NOT NULL AUTO_INCREMENT COMMENT '序号';
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
