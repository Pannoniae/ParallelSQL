package net.pannoniae

class Stats {
    static void main(String[] args) {
        def src = args.length > 0 ? args[0] : "/home/pannoniae/sch/HPC/ParallelSQL/db/"

        println partition(counts(src), 4)
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

    static List<String> partition(Map<String, Long> lineCounts, int nodes) {
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

        List<String> result = []
        parts.eachWithIndex { part, i ->
            result << "Node ${i + 1}: ${part.join(', ')}"
        }

    }
}