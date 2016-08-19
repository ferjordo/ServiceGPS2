<?php
require_once 'conexion.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
$body = json_decode(file_get_contents("php://input"), true);
$conexion = new mysqli(HOSTNAME, USERNAME, PASSWORD, DATABASE);
$latitud = $_GET['latitud'];
$longitud = $_GET['longitud'];
$hora = $_GET['hora'];
if ($conexion->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql = "insert into lugar (latitud,longitud,hora) values('" .
	$latitud ."','" . $longitud .
	"','" . $hora . "')";
if ($conexion->query($sql)===true){
    print json_encode(
            array(
                'estado' => '1',
                'mensaje' => 'Se ha creado un registro')
        );
} else{
    print json_encode(
            array(
                'estado' => '2',
                'mensaje' => 'Creacion fallida')
        );
}
$conexion->close();
}
?>