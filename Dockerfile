FROM payara/server-full:5.193.1
ENV USE_WORKAROUND=false
COPY target/*.war $DEPLOY_DIR
#for 5.183 and before use $AUTODEPLOY_DIR
#COPY target/*.war $AUTODEPLOY_DIR