/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.thrift.TException;

// Generated code
import io.opentracing.thrift.SpanProtocol;
import tutorial.*;
import shared.*;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;


import io.opentracing.util.GlobalTracer;
import io.opentracing.Tracer;
import io.jaegertracing.Configuration;

import java.util.HashMap;

public class CalculatorHandler implements Calculator.Iface {

  private HashMap<Integer,SharedStruct> log;
  private TTransport transport;
  private Calculator.Client client;

  public CalculatorHandler() {
    log = new HashMap<Integer, SharedStruct>();

    try {
      transport = new TSocket("localhost", 19090);
      transport.open();

      TProtocol protocol = new  TBinaryProtocol(transport);
      client = new Calculator.Client(new SpanProtocol(protocol, GlobalTracer.get()));

    } catch (TException x) {
      x.printStackTrace();
    }

  }

  public void ping() throws TException {
    System.out.println("ping()");
    client.ping();
  }

  public int add(int n1, int n2)  throws TException {
    System.out.println("add(" + n1 + "," + n2 + ")");
    return client.add(n1, n2);
  }

  public int calculate(int logid, Work work) throws TException {
    System.out.println("calculate(" + logid + ", {" + work.op + "," + work.num1 + "," + work.num2 + "})");
    return client.calculate(logid, work);

//    int val = 0;
//    switch (work.op) {
//    case ADD:
//      val = work.num1 + work.num2;
//      break;
//    case SUBTRACT:
//      val = work.num1 - work.num2;
//      break;
//    case MULTIPLY:
//      val = work.num1 * work.num2;
//      break;
//    case DIVIDE:
//      if (work.num2 == 0) {
//        InvalidOperation io = new InvalidOperation();
//        io.whatOp = work.op.getValue();
//        io.why = "Cannot divide by 0";
//        throw io;
//      }
//      val = work.num1 / work.num2;
//      break;
//    default:
//      InvalidOperation io = new InvalidOperation();
//      io.whatOp = work.op.getValue();
//      io.why = "Unknown operation";
//      throw io;
//    }
//
//    SharedStruct entry = new SharedStruct();
//    entry.key = logid;
//    entry.value = Integer.toString(val);
//    log.put(logid, entry);
//
//    return val;
  }

  public SharedStruct getStruct(int key) throws TException{
    System.out.println("getStruct(" + key + ")");
//    return log.get(key);
    return client.getStruct(key);
  }

  public void zip() throws TException{
    System.out.println("zip()");
    client.zip();
  }

}

