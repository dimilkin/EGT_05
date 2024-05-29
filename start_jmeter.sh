export HEAP="-Xms2g -Xmx2g -XX:MaxMetaspaceSize=512m"

TEST_PLAN="egt_gateway.jmx"
TIMESTAMP=$(date +%Y%m%d%H%M%S)

RESULTS_FILE="jmeterresults/results_${TIMESTAMP}.jtl"
REPORTS_FOLDER="reports_${TIMESTAMP}"

jmeter -n -t $TEST_PLAN -l $RESULTS_FILE -e -o $REPORTS_FOLDER