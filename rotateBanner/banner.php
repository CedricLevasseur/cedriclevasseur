<?php
$handle = fopen("http://localhost/rotateBanner/banner.json", "rb");
$contents = '';
while (!feof($handle)) {
  $json .= fread($handle, 8192);
}
fclose($handle);

if (isset($_GET['callback'])) {
    echo $_GET['callback'].'('.$json.');';
} else {
    echo $json;
}
?>
