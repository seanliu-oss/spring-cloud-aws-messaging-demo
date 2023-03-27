#!/bin/bash
AWSLOCAL=/usr/local/bin/awslocal
REQUEST_QUEUE=sqs-request
REQUEST_DLQ=sqs-request-dlq
RESULT_QUEUE=sqs-result
LOCALSTACK_ACCOUNT_ID=0000000000
LOCALSTACK_ENDPOINT=http://localhost:4566

#Create queues
for queue in $REQUEST_QUEUE $REQUEST_DLQ $RESULT_QUEUE;
do
#  ${AWSLOCAL} sqs create-queue --endpoint-url ${LOCALSTACK_ENDPOINT} --queue-name $queue --region "${AWS_DEFAULT_REGION}"
  ${AWSLOCAL} sqs create-queue --queue-name $queue

done

#Linkup DLQ
REQUEST_QUEUE_URL=${LOCALSTACK_ENDPOINT}/${LOCALSTACK_ACCOUNT_ID}/${REQUEST_QUEUE}
DLQ_ARN=arn:aws:sqs:${AWS_DEFAULT_REGION}:${LOCALSTACK_ACCOUNT_ID}:${REQUEST_DLQ}
#${AWSLOCAL} sqs set-queue-attributes --endpoint-url ${LOCALSTACK_ENDPOINT} --queue-url ${REQUEST_QUEUE_URL} \
#  --attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\":\"'${DLQ_ARN}'\", \"maxReceiveCount\":\"1\"}"}' \
#  --region "${AWS_DEFAULT_REGION}"

${AWSLOCAL} sqs set-queue-attributes --queue-url ${REQUEST_QUEUE_URL} \
  --attributes '{"RedrivePolicy": "{\"deadLetterTargetArn\":\"'${DLQ_ARN}'\", \"maxReceiveCount\":\"1\"}"}'

#List queues
#${AWSLOCAL} sqs list-queues --endpoint-url ${LOCALSTACK_ENDPOINT} --region "${AWS_DEFAULT_REGION}"
${AWSLOCAL} sqs list-queues