CREATE TABLE `cuenta` (
  `id_cuenta` bigint(20) NOT NULL AUTO_INCREMENT,
  `dni_cliente` int(11) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `num_cuenta` int(11) DEFAULT NULL,
  `saldo_disponible` decimal(38,2) DEFAULT NULL,
  `saldo_inicial` decimal(38,2) DEFAULT NULL,
  `tipo_cuenta` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_cuenta`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- sql10719246.movimiento definition

CREATE TABLE `movimiento` (
  `id_movimiento` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `saldo` decimal(38,2) DEFAULT NULL,
  `tipo_movimiento` tinyint(4) DEFAULT NULL,
  `valor` decimal(38,2) DEFAULT NULL,
  `cuenta_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id_movimiento`),
  KEY `FK4ea11fe7p3xa1kwwmdgi9f2fi` (`cuenta_id`),
  CONSTRAINT `FK4ea11fe7p3xa1kwwmdgi9f2fi` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id_cuenta`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;