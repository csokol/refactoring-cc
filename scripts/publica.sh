set -e
rm *.png;R --save < charts.R 

#cp plot2.png ~/ime/mestrado/artigos/msr2013/replication/img/nonrefactorings-decrease.png
#cp plot1.png ~/ime/mestrado/artigos/msr2013/replication/img/refactorings-decrease.png
cp plot.png ~/ime/mestrado/artigos/msr2013/replication/img/commits-decreasing.png
cp plot-apache-ant.csv.png ~/ime/mestrado/artigos/msr2013/replication/img/graficos/ant.png
cp plot-apache-tomcat.csv.png ~/ime/mestrado/artigos/msr2013/replication/img/graficos/tomcat.png
cp plot-apache-camel.csv.png ~/ime/mestrado/artigos/msr2013/replication/img/graficos/camel.png

cd ~/ime/mestrado/artigos/msr2013/replication/
make clean
make

cd -


