/**
Copyright [2014] jiucai.org

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package org.jiucai.appframework.base;

import javax.swing.JOptionPane;

import org.jiucai.appframework.Version;

/**
 * base lib main class
 *
 * @author zhaidw
 *
 */
public class Main {

    /**
     * Main
     *
     * @param args
     *            args
     */
    public static void main(String[] args) {
        System.out.println("This is base lib for org.jiucai.appframework");
        JOptionPane.showMessageDialog(null, "This is appframework from org.jiucai.appframework",
                "appframework " + Version.VersionNumber, JOptionPane.INFORMATION_MESSAGE);

    }

}
