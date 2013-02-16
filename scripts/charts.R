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

jpeg('plot1.png')
plot(withRefactorings$total.commits, 
    withRefactorings$refactorings.decreasing.ratio.doc, 
    col="blue", pch=19, 
    main="Documented refactorings decreasing CC \n ratio versus commit count")
dev.off()


jpeg('plot2.png')
plot(withRefactorings$total.commits, 
    withRefactorings$refactorings.decreasing.ratio.undoc, 
    col="blue", pch=19, ylim=c(0,1.0), 
    main="Common commits decreasing CC ratio \n versus commit count")
dev.off()

