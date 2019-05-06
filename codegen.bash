# this script is called when the judge wants our compiler to compile a source file.
# print the compiled source, i.e. asm code, directly to stdout.
# don't print anything other to stdout.
# if you would like to print some debug information, please go to stderr.

set -e
cd "$(dirname "$0")"
export M_="java -classpath ./lib/antlr-4.7.2-complete.jar:./bin Mx.Main"
#cat > program.cpp   # save everything in stdin to program.txt
$M_
#cat program.asm