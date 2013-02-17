rm *.png;R --save < charts.R 

cp plot2.png ~/ime/mestrado/artigos/msr2013/replication/img/nonrefactorings-decrease.png
cp plot1.png ~/ime/mestrado/artigos/msr2013/replication/img/refactorings-decrease.png

cd ~/ime/mestrado/artigos/msr2013/replication/
make clean
make

cd -


