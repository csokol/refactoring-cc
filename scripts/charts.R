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

png('plot1.png')
plot(withRefactorings$total.commits, 
    withRefactorings$refactorings.decreasing.ratio.doc * 100, 
    col="blue", pch=19, ylim=c(0,100), 
    ylab="Percentage of documented refactorings decreasing CC", 
    xlab="Total commit count", 
    main="Documented refactorings decreasing CC \n versus project commit count")
dev.off()


png('plot2.png')
plot(withRefactorings$total.commits, 
    withRefactorings$refactorings.decreasing.ratio.undoc * 100, 
    col="blue", pch=19, ylim=c(0,100), 
    ylab="Percentage of common commits decreasing CC", 
    xlab="Total commit count", 
    main="Common commits decreasing CC \n versus commit count")
dev.off()

