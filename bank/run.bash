qpdf --decrypt --password='' AXA-Banque-Relevé-Cpte-1242214-01-201809-Septembre-2018.pdf AXA-Banque-Relevé-Cpte-1242214-01-201809-Septembre-2018-decrypted.pdf
pdf2txt.py AXA-Banque-Relevé-Cpte-1242214-01-201809-Septembre-2018-decrypted.pdf > AXA-Banque-Relevé-Cpte-1242214-01-201809-Septembre-2018-decrypted.txt
alias groovy='/opt/groovy-3.0.0-alpha-3/bin/groovy'
groovy axa.groovy -file AXA-Banque-Relevé-Cpte-1242214-01-201809-Septembre-2018-decrypted.pdf
