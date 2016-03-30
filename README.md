JNS3 is a real-time network simulator written in Java. It is a Java implementation of the GNS3 simulator. It is by no means as complete as GNS3, but it is a lot more accessible to programmers familiar with Java. The source code is released under the GNU General Public License, version 2. JNS3 is intended to be used as a learning tool.

The project was done as an assignment for a computer networking concepts at the University of North Carolina at Greensboro, under the supervision of Prof. Shan Suthaharan. For more details see <a>http://dl.acm.org/citation.cfm?id=2656464</a>

JNS3 make use of other open source projects including:

Qemu <a>http://wiki.qemu.org/Main_Page</a>

Quagga <a>http://www.nongnu.org/quagga/</a>

Tiny Core Linux <a>http://tinycorelinux.net/</a>

Each node runs a Tiny Core Linux image emulated as a Qemu guest. The router node runs the Quagga routing suite on Tiny Core Linux. Networking between the nodes is provides using UDP Tunneling. Each link end is a UDP server that receives the UDP tunneled traffic from UDP tunnel network device of the Qemu virtual machine and forward it to the other node.
The switch node runs a UDP server for each port, that receives the UDP tunneled traffic from the link, and forward it to the other links.

<b>Usage:</b>

To use JNS3 you need to inherit the abstract class Simulation, and override the run() method.

The initial configuration of the devices should be placed in the root of the config directory.



<b>Todo list:</b>

Replace Putty with a Java based terminal emulator (telnet support only required).

Provide drag and drop capabilities for the construction of the simulation topology.

Replace Qemu with a Java based x86 emulator (e.g. JPC).

Add more packet decoding capabilities. Currently only IPv4 packets are supported.

Provide a flexible filtering mechanism for the packet visualization.

Add more modem types. Currently PSK and FM modems are supported.

Add a Noise model to simulate noise signal on the lines.

Add the VLANs and the IEEE802.1Q support to the switch device.

Generate Javadoc for the code.
