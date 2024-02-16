-- ejecutar este select, copiar y pegar el resultado abajo entre los set (linea 10)
SELECT CONCAT('DROP TABLE IF EXISTS ', table_name, ';')
FROM information_schema.tables
WHERE table_schema = 'tpa';
-- End


SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS centroide;
DROP TABLE IF EXISTS comunidad;
DROP TABLE IF EXISTS comunidad_miembro;
DROP TABLE IF EXISTS entidad;
DROP TABLE IF EXISTS entidad_estacion;
DROP TABLE IF EXISTS entidad_servicio;
DROP TABLE IF EXISTS horarios_alerta;
DROP TABLE IF EXISTS incidente;
DROP TABLE IF EXISTS localizacion;
DROP TABLE IF EXISTS miembro;
DROP TABLE IF EXISTS organizacion;
DROP TABLE IF EXISTS servicio;
DROP TABLE IF EXISTS tipo_usuario;
DROP TABLE IF EXISTS tipo_usuario_entidad;
DROP TABLE IF EXISTS tipo_usuario_miembro;
DROP TABLE IF EXISTS tipo_usuario_servicio;
DROP TABLE IF EXISTS tipoalerta;

SET FOREIGN_KEY_CHECKS = 1;