# BigMethodScanner
Scans for Methods within projects with a bytecode size larger than 8k. As methods larger than this will not be eligible for JIT Compilation
The Scanner will recursively scan within jars,zip, ear files for classes with large methods.
Usage : java -jar BigMethodScanner.jar -P /path/to/project/directory
