results = read.csv("../resultados.csv", sep=";")
cleanResults = results[1:256,]

cleanResults$documented.refactorings.total =  cleanResults$documented.refactorings.decreasing.cc +
    cleanResults$documented.refactorings.increasing.cc +
    cleanResults$documented.refactorings.equalizing.cc

cleanResults$undocumented.refactorings.total = cleanResults$undocumented.refactorings.decreasing.cc + 
    cleanResults$undocumented.refactorings.equalizing.cc + 
    cleanResults$undocumented.refactorings.increasing.cc

cleanResults$total.commits = cleanResults$documented.refactorings.decreasing.cc + 
    cleanResults$documented.refactorings.increasing.cc +
    cleanResults$documented.refactorings.equalizing.cc +
    cleanResults$undocumented.refactorings.equalizing.cc +
    cleanResults$undocumented.refactorings.increasing.cc +
    cleanResults$undocumented.refactorings.decreasing.cc

withRefactorings = cleanResults[cleanResults$documented.refactorings.total > 0,]

withRefactorings$refactorings.decreasing.ratio.doc = withRefactorings$documented.refactorings.decreasing.cc / withRefactorings$documented.refactorings.total
withRefactorings$refactorings.decreasing.ratio.undoc = withRefactorings$undocumented.refactorings.decreasing.cc / withRefactorings$undocumented.refactorings.total

png('plot.png', width=2*480)
par(mfrow=c(1,2))
plot(withRefactorings$total.commits, 
    withRefactorings$refactorings.decreasing.ratio.doc * 100, 
    col="blue", pch=19, ylim=c(0,100), 
    ylab="Percentage of documented refactorings decreasing CC", 
    xlab="Total commit count", 
    main="(a) Documented refactorings decreasing CC \n versus project commit count")
plot(withRefactorings$total.commits, 
    withRefactorings$refactorings.decreasing.ratio.undoc * 100, 
    col="blue", pch=19, ylim=c(0,100), 
    ylab="Percentage of common commits decreasing CC", 
    xlab="Total commit count", 
    main="(b) Common commits decreasing CC \n versus commit count")
dev.off()

print(median(withRefactorings$refactorings.decreasing.ratio.doc) * 100)
print(median(withRefactorings$refactorings.decreasing.ratio.undoc) * 100)

png('boxplot.png', width=2*480, height=2*480)
lmts <- range(0,100)
par(mfrow = c(1, 2))
boxplot(withRefactorings$refactorings.decreasing.ratio.undoc * 100, ylim=lmts)
boxplot(withRefactorings$refactorings.decreasing.ratio.doc * 100, ylim=lmts)
dev.off()

files = dir("../csv/")
plotIt <- function(name, header) {
    complete_name = paste("../csv/", name, sep="") 
    csvfile = read.csv(complete_name, sep=";")
    png_name = paste("plot-", name, ".png", sep="")
    png(png_name, width=1.70*480)
    plot(csvfile, type="n", ylim=c(0, 30000)
        ylab="Total Cyclomatic Complexity",
        xlab="Version",
        main=header)
    lines(csvfile, col="blue", ylim=c(0, 30000))
    #text(900, 13800, "(a)", cex=1.5)
    dev.off()
}

plotIt("apache-ant.csv", "Apache Ant")
plotIt("apache-tomcat-7-0-x.csv", "Apache Tomcat 7.0")
plotIt("apache-camel.csv", "Apache Camel")




