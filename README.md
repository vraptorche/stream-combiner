# Implementation
Implementation consists of two main parts.
 
### Stream Generator

Stream generator is implemented as testing facility for data generation. It runs 
on one host and can be configured to listen to n different ports and generate
streams of input data for the main application. It generates infinite streams of 
data, in chunks of random  number data. Maximum count of elements in a chunk is 
configurable. It is possible every stream to be generated with different speed.

### Stream Combiner

Stream combiner works on n streams, to which it is connected via network sockets.
Host and port for every stream is configurable. There is no restriction that all 
streams should come from the same host.

All streams are merged into one and this resulting stream is fed into processing 
part. I pipeline of processing steps is assembled and data flow thru.
Data comes in as XML and transforms in internal immutable object. Next a window 
with duration of 250 ms (this should be tuned) and all data items in this window 
are grouped by timestamp. Result is bunch of substreams holding the same timestamp
and different amount. On every substream a reduction step is performed - meaning 
that all amounts in the substream are summed up and from every substream only one 
value is generated. These steps are performed in parallel. All resulting data items 
are the emitted in the main stream. Last step is transforming every data item from 
internal object to JSON representation. 

##Configuration

### Configuration of Stream Combiner Application

The configuration is done in file named application.yml, which is part of the distribution
or can be provided externally.
List of host/port pairs are given, under unique names according the YAML syntax.
It is important that these name are unique because are used as keys in a map.


### Configuration of Generator Application

The configuration is done in file named application.yml, which is part of the distribution
or can be provided externally.
List of host/port pairs are given, under unique names according the YAML syntax.
It is important that these name are unique because are used as keys in a map.
Maximum number of elements is also part of the configuration.
Server port is defined, and has to be different form the one used by the combiner
if both applications run on the same host.



