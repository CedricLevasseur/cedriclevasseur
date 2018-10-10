import groovy.cli.picocli.CliBuilder

    def cli = new CliBuilder(usage:'groovy axa.groovy -file /tmp/file0.pdf where groovy>=3', header:'Options')
    cli.file(type:File, argName:'file', 'use given file')
    def options = cli.parse(args)
    assert options // would be null (false) on failure
     
    // read all the content of the file into a single string
    File fh1 = options.file
    text = fh1.getText('UTF-8')
     
    // read all the lines into a list, each line is an element in the list
    File fh2 = options.file
    def lineNumber=0, start=0, end=0
    def lines = fh2.readLines()
    for (line in lines) {
        lineNumber++
        if((line.contains('Nature de l\'opération')) && (start==0)){
            start=lineNumber+5
        }
        if((end==0)&&(start>0)&&(lineNumber>start+1)&&(line.isEmpty())){
            end=lineNumber
        }
    }
    println "total lines:$lineNumber,starting from $start ,ending to $end"
    lineNumber=0
    def regexDate=/(0?[1-9]|[12][0-9]|3[01])\.(0?[1-9]|1[012])\.\d{4}.*$/
    regexDate=/\d{2}\.\d{2}\.\d{4}.*$/
    regexNature=/[0-9A-Z°\/ ]+$/
    regexNotNature=/[0-9\/]+$/
    regexAmount=/([1-9]{1,3} ){0,1}[0-9]{1,3}(,[0-9]{0,2}){0,1}/
//    regexAmount=/[0-9,]+$/
    regexNotAmount=/[A-Za-z\/]*/
    assert "26.09.2018".matches(regexDate)
    def previousLineMatches=false
    def date = [], nature = [], amount = []
    for (line in lines) {
        lineNumber++
        
        if(lineNumber>=start){
            if(line.matches(regexDate)){
                    date.add(line)
            }
            if((!line.matches(regexNotNature))&&(line.matches(regexNature))){
                    nature.add(line)
            }
            if((!line.matches(regexNotAmount))&&(line.matches(regexAmount))){
                    amount.add(line)
            }
        }
    }
    println date.size 
    println nature
    println amount.size
    def data = []
    (0..date.size-1).each(){
         println it
         data.add([ date.get(it), nature.get(it), amount.get(it) ] ) } 
    println data 

    

