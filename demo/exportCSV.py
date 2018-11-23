import sys
import mdfreader
yop=mdfreader.mdf(sys.argv[1])
yop.exportToCSV(sampling=0.1)
print(yop.keys())
