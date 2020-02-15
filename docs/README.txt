+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					EXTENSIBLE:
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

To API was designed intentionally to be extensible so that new feed types can be easily added. Adding a new feed type feature processing essentially involves adding:   

1) Task -> File Processor (com.acme.services.push.thread.tasks)
2) File Processor [XML] (com.acme.services.push.file.xml)

CODE CHANGES:

add class for processing the xml input file to the com.acme.services.push.thread.tasks package extends AbstractProcessFile.class -> XXXXFileProcessor.class
add xml file parser to the  com.acme.services.push.file.xml package extends GenericFileParser.class -> XXXXXFileParser.class

add LABEL_[XXXXX]_TYPE to Constants.class
e.g.
	public final static String LABEL_XXXXX_TYPE = "XXXXX";

ParserFacade (java):

getFeedType(): add case for new feed type to switch
e.g.

	switch(action) {
	
		case Constants.LABEL_XXXXX_TYPE:
			
			type = FeedType.XXXXX;
			break;

FeedType (Java):
e.g.
	TRACK, ORDER, XXXXX, INVALID;

	
CONFIGURATUION CHANGES:

Config.properties:

#configuration settings for the push service (XXXXX Request)
user.track.endpoint=https://api.push.com/users/[label]
user.track.method=POST
user.track.contenttype=application/json

e.g.
data.transform.reference.XXXXX=[XXXXX_LABEL]

e.g.
# define mapping key for non order file feeds
data.feed.type.reference.list=ORDER,TRACK,XXXXX
data.feed.type.reference.TRACK.id=TRACK
data.feed.type.reference.ORDER.id=ORDER
data.feed.type.reference.XXXXX.id=XXXXX

e.g.
# specific data settings for XXXXX Service 
data.feed.type.reference.XXXXX.fields.list=app_group_id
data.feed.type.reference.XXXXX.field.app_group_id.value=qo0wei23ru2-3ir3-r234otr234i-ebverb
data.feed.type.reference.XXXXX.fields.data.root=[RECORDS_ROOT]

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					DEPLOY INSTRUCTIONS [DEV]:
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

---------------------------------------------------------------------------------------------------
maven clean
maven build [api package -> jar package.]
copy all deploy files etc. into the [outgoing] folder

---------------------------------------------------------------------------------------------------

psftp (PuTTy)
lcd c:\outgoing\
cd /home/[user]/incoming
put files in [outgoing] folder -> mput *.*

---------------------------------------------------------------------------------------------------

putty into [jumpserver]
open [jumpserver]

scp /home/[user]/incoming/*.* [user]@[job server]/tmp e.g. cgordon@jbocodev02:tmp/
ssh [job server] e.g. ssh jbocodev02
elevate priveledges to jobuser -> dzdo su - jobuser
cd push/app  [pwd=/opt/jobuser]
copy jar to app folder -> cp /tmp/PushAPI-0.0.1-SNAPSHOT.jar .
---------------------------------------------------------------------------------------------------

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					XML:
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

url: http://[domain]/salta/process/getBannerDetails

method: POST

xml:
<eps:esbSaltaServiceRequest xmlns:eps="http://www.eps.flws.salta.in.com">
	<eps:getBannerCodeDetails>
		<eps:siteId>18F</eps:siteId>
		<eps:sourceSystem>Web</eps:sourceSystem>
		<eps:storeId>20051</eps:storeId>
		<eps:bannerCode>02858</eps:bannerCode>
	</eps:getBannerCodeDetails>
</eps:esbSaltaServiceRequest>

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					SYNTAX:
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

scp /home/directory/file.name(*.*)  cgordon@jsfnyprod04:/destination/folder
dzdo su - jobuser
ssh server.name
tar -cvf tarfile.tar path/to/file [compress]
tar -xvf tarfile.tar [extract]
cat /etc/*-release

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					INITIAL DEPLOY [MOVE TO PROD]:
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

putty into jbocodev02
dzdo su - jobuser
cd /tmp
mkdir deploy
cd /opt/jobuser
tar -cvf PushDeploy.tar push
cp PushDeploy.tar /tmp/deploy
exit (to jumpserver - jumpbocoprod01)
scp cgordon@jbocodev02:/tmp/deploy/*.* /home/cgordon/deploy

scp /home/cgordon/PushDeploy.tar cgordon@jsfnyprod04:/home/cgordon/deploy

putty into jsfnyprod04
cd /home/cgordon/deploy
cp pushDeploy.tar /tmp 

dzdo su - jobuser
cd /opt/jobuser
mkdir pushTMP
cp /tmp/pushDeploy.tar .
tar -xvf pushDeploy.tar

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					MISCELLANEOUS:
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Please note that log files and archives etc should not be on the /opt linux drive mount as these can 
potentially fill up the mount. log files etc should be on the /var mount which can fill up go 
offline etc without degradation of the applications.

Log Files:

putty into jsfnyprod04

cd /var/log/jobuser
mkdir push
mkdir logs
mkdir archive

log folder "/var/log/jobuser/push/logs"
archive folder "/var/log/jobuser/push/archive"

