package net.pannoniae

import java.nio.file.Path

class Stats {

    static final String PATH = System.getenv("PARALLELSQL_DIR") != null ?
            System.getenv("PARALLELSQL_DIR") + "/db/" :
            "/home/pannoniae/sch/HPC/ParallelSQL/db/"
    static final String QUERIES_PATH = System.getenv("PARALLELSQL_DIR") != null ?
            System.getenv("PARALLELSQL_DIR") + "/queries/" :
            "/home/pannoniae/sch/HPC/ParallelSQL/queries/"

    static void main(String[] args) {
        def src = args.length > 0 ? args[0] : PATH

        println partition(counts(src), 1)
        println partition(counts(src), 2)
        println partition(counts(src), 4)
        println partition(counts(src), 8)
        println partition(counts(src), 16)
    }


    static void writeFile(String folder) {
        var f = new File(folder).listFiles().findAll { it.name.endsWith(".tbl") }
        var out = new File("lines.txt")
        out.text = "" // clear the file first
        f.each { file ->
            def c = file.readLines().size()
            out.append("${file.name};${c}\n")
        }
    }

    static Map<String, Long> readFile() {
        return new File("lines.txt").readLines().collectEntries {
            def split = it.split(";")
            [(split[0]): split[1] as long]
        }
    }

    static Map<String, Long> counts(String folder) {
        var result = new HashMap<String, Long>()
        new File(folder).listFiles().findAll { it.name.endsWith(".tbl") }.each { file ->
            result[file.name] = file.readLines().size() as long
        }
        return result
    }

    static List<List<String>> partition(Map<String, Long> lineCounts, int nodes) {
        var data = lineCounts.collect { name, size ->
            [name: name, size: size]
        }.sort { -it.size }

        var parts = (0..<nodes).collect { [] }
        var sizes = new long[nodes]

        data.each { f ->
            var min = (0..<nodes).min { sizes[it] }
            parts[min] << f.name
            sizes[min] += f.size
        }
        return parts

    }
}