-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 27, 2019 at 09:06 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `modul6_kel32`
--

-- --------------------------------------------------------

--
-- Stand-in structure for view `db_karyawan`
-- (See below for the actual view)
--
CREATE TABLE `db_karyawan` (
);

-- --------------------------------------------------------

--
-- Table structure for table `kamar`
--

CREATE TABLE `kamar` (
  `id_kamar` varchar(12) NOT NULL,
  `kelas` varchar(100) NOT NULL,
  `harga` int(255) NOT NULL,
  `fasilitas` varchar(1000) NOT NULL,
  `status` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kamar`
--

INSERT INTO `kamar` (`id_kamar`, `kelas`, `harga`, `fasilitas`, `status`) VALUES
('KE-01', 'Ekonomis', 500000, 'Singe Bed, Shower, AC', 'Tersedia'),
('KE-02', 'Ekonomis', 500000, 'Singe Bed, Shower, AC', 'Tersedia'),
('KE-03', 'Ekonomis', 500000, 'Singe Bed, Shower, AC', 'Tersedia'),
('KE-04', 'Ekonomis', 500000, 'Singe Bed, Shower, AC', 'Tersedia'),
('KE-05', 'Ekonomis', 500000, 'Singe Bed, Shower, AC', 'Tersedia'),
('KK-01', 'Keluarga', 1200000, '2 Kasur Double, AC, Water Heater, Bath tub', 'Tersedia'),
('KK-02', 'Keluarga', 1200000, '2 Kasur Double, AC, Water Heater, Bath tub', 'Tersedia'),
('KK-03', 'Keluarga', 1200000, '2 Kasur Double, AC, Water Heater, Bath tub', 'Tersedia'),
('KK-04', 'Keluarga', 1200000, '2 Kasur Double, AC, Water Heater, Bath tub', 'Tersedia'),
('KK-05', 'Keluarga', 1200000, '2 Kasur Double, AC, Water Heater, Bath tub', 'Tersedia'),
('KV-01', 'VVIP', 1000000, 'Double Bed, AC, TV, DVD, Expresso, Water Heater', 'Tersedia'),
('KV-02', 'VVIP', 1000000, 'Double Bed, AC, TV, DVD, Expresso, Water Heater', 'Tersedia'),
('KV-03', 'VVIP', 1000000, 'Double Bed, AC, TV, DVD, Expresso, Water Heater', 'Disewa'),
('KV-04', 'VVIP', 1000000, 'Double Bed, AC, TV, DVD, Expresso, Water Heater', 'Tersedia'),
('KV-05', 'VVIP', 1000000, 'Double Bed, AC, TV, DVD, Expresso, Water Heater', 'Tersedia');

-- --------------------------------------------------------

--
-- Table structure for table `kondisi`
--

CREATE TABLE `kondisi` (
  `id_kondisi` int(11) NOT NULL,
  `id_kamar` varchar(12) NOT NULL,
  `tgl` varchar(12) NOT NULL,
  `status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kondisi`
--

INSERT INTO `kondisi` (`id_kondisi`, `id_kamar`, `tgl`, `status`) VALUES
(1, 'KE-02', '24/11/2019', 'CekIn'),
(2, 'KE-02', '25/11/2019', 'CekIn'),
(5, 'KE-03', '24/11/2019', 'CekIn'),
(28, 'KE-02', '28/11/2019', 'CekIn'),
(29, 'KE-02', '29/11/2019', 'CekIn');

-- --------------------------------------------------------

--
-- Table structure for table `sewa`
--

CREATE TABLE `sewa` (
  `id_sewa` int(12) NOT NULL,
  `id_kamar` varchar(12) NOT NULL,
  `tgl_masuk` varchar(12) NOT NULL,
  `tgl_keluar` varchar(12) NOT NULL,
  `username` varchar(100) NOT NULL,
  `hargatotal` int(255) NOT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sewa`
--

INSERT INTO `sewa` (`id_sewa`, `id_kamar`, `tgl_masuk`, `tgl_keluar`, `username`, `hargatotal`, `status`) VALUES
(1, 'KE-02', '24/11/2019', '25/11/2019', 'ical', 1000000, 'CekIn'),
(2, 'KE-03', '24/11/2019', '24/11/2019', 'muharrik', 500000, 'CekOut'),
(10, 'KE-02', '28/11/2019', '29/11/2019', 'ical', 1000000, 'CekIn');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nik` varchar(100) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `asal` varchar(100) NOT NULL,
  `saldo` int(255) NOT NULL,
  `role` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`, `nik`, `nama`, `asal`, `saldo`, `role`) VALUES
('daud', 'daud', '1928371982', 'Daud Dimas', 'Semarang', 0, 'Member'),
('ical', 'ical', '12103221339', 'Faisal', 'Banten', 33400000, 'Member'),
('muharrik', 'muharrik', '21120116130099', 'Muharrik', 'Jakarta', 154000000, 'Member'),
('raykisna', 'raykisna', '21120116130060', 'RIo Kisna', 'Mranggen', 0, 'Admin'),
('tidakada', 'tidakada', '21392193012', 'Tidak Ada Kepastian', 'Jakarta', 0, 'Member');

-- --------------------------------------------------------

--
-- Structure for view `db_karyawan`
--
DROP TABLE IF EXISTS `db_karyawan`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `db_karyawan`  AS  select `k`.`nip` AS `nip`,`k`.`nama` AS `nama`,`k`.`alamat` AS `alamat`,`k`.`tahun_masuk` AS `tahun_masuk`,`d`.`nama_departemen` AS `nama_departemen`,`d`.`jabatan` AS `jabatan` from (`karyawan` `k` left join `departemen` `d` on((`k`.`id_departemen` = `d`.`id_departemen`))) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id_kamar`);

--
-- Indexes for table `kondisi`
--
ALTER TABLE `kondisi`
  ADD PRIMARY KEY (`id_kondisi`),
  ADD UNIQUE KEY `tglkamar` (`id_kamar`,`tgl`);

--
-- Indexes for table `sewa`
--
ALTER TABLE `sewa`
  ADD PRIMARY KEY (`id_sewa`),
  ADD KEY `fk_sewa_user` (`username`),
  ADD KEY `fk_sewa_kamar` (`id_kamar`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kondisi`
--
ALTER TABLE `kondisi`
  MODIFY `id_kondisi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `sewa`
--
ALTER TABLE `sewa`
  MODIFY `id_sewa` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `kondisi`
--
ALTER TABLE `kondisi`
  ADD CONSTRAINT `fk_kamar_kondisi` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`) ON DELETE CASCADE;

--
-- Constraints for table `sewa`
--
ALTER TABLE `sewa`
  ADD CONSTRAINT `fk_sewa_kamar` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_sewa_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
