<html><head><title>Count</title><style>* {font-size : 144pt;}</style><body>

<?php
// Affiche le nom d'utilisateur qui fait tourner le processus php/http
// (sur un système ayant "whoami" dans le chemin d'exécutables)
echo exec('cat  /var/log/httpd/access_log | grep "vide.gif"  | wc -l');
?>
</body></html>
