<?php

//$d = array('http://www.numerikids.com/alpha/wp-content/uploads/2009/06/mario_bros.jpg',  'http://monophonik.com/wp-content/uploads/2009/06/mario-wii.jpg', 'http://www.nintendolesite.com/images/news/news_Nouveau_Mario_en_approche_titre.jpg');
$d1= array("src"=>"http://www.numerikids.com/alpha/wp-content/uploads/2009/06/mario_bros.jpg","link"=>"http://www.google.fr");
$d2= array("src"=>"http://monophonik.com/wp-content/uploads/2009/06/mario-wii.jpg","link"=>"http://www.bing.com");
$d3= array("src"=>"http://www.nintendolesite.com/images/news/news_Nouveau_Mario_en_approche_titre.jpg","link"=>"http://www.yahoo.fr");

$d=array($d1,$d2,$d3);

echo  json_encode($d), "\n";

?>
