package net.pannoniae

import cluster_cli.parse.Parser

class RunParserDirectory {
    static void main(String[] args) {
        String workingDirectory = System.getProperty("user.dir")
        // for all files in the directory
        // only files with a clic extension
        for (filename in new File("$workingDirectory/src/main/groovy/DSLfiles").listFiles({
            f, name ->
                name.endsWith(".clic")
        } as FilenameFilter)) {
            var stripped = filename.name.split("\\.")[0]
            Parser parser = new Parser("$workingDirectory/src/main/groovy/DSLfiles/" + stripped)
            assert parser.parse(): "Parsing failed"
        }
    }
}
