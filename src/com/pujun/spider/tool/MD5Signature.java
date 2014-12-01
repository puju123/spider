/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pujun.spider.tool;

import java.security.MessageDigest;

/**
 * Default implementation of a page signature. It calculates an MD5 hash
 * of the raw binary content of a page. In case there is no content, it
 * calculates a hash from the page's URL.
 *
 * @author Andrzej Bialecki &lt;ab@getopt.org&gt;
 */
public class MD5Signature {

  public static String calculate(String s) {
      char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

      try {
          byte[] btInput = s.getBytes();
          // 获得MD5摘要算法的 MessageDigest 对象
          MessageDigest mdInst = MessageDigest.getInstance("MD5");
          // 使用指定的字节更新摘要
          mdInst.update(btInput);
          // 获得密文
          byte[] md = mdInst.digest();
          // 把密文转换成十六进制的字符串形式
          int j = md.length;
          char str[] = new char[j * 2];
          int k = 0;
          for (int i = 0; i < j; i++) {
              byte byte0 = md[i];
              str[k++] = hexDigits[byte0 >>> 4 & 0xf];
              str[k++] = hexDigits[byte0 & 0xf];
          }
          return new String(str);
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }
  }
}
