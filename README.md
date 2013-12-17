DISCLAIMER!
-----------

This project is no longer maintained and a more recent version can be found 
__[here](https://github.com/supranove/clockwork)__.

Swing is a pretty good GUI toolkit but it is rather outdated, and this shows when building
complex GUIs. As such, I'm moving on to Qt/C++.

Huh, Qt/C++? That's too radical a change. Why not switch to JavaFX? Well because my primary
concern was writing a platform-agnostic application and it just so turns out that Qt offers
this possibility. Secondly, I'm much more comfortable programming in C/C++ (go figure...).
Lastly, and most importantly, there's no overhead introduced when writing C++ code as opposed
to the Java bytecode-to-virtual-machine pipeline. This lack of overhead is a plus since 
Clockwork is not hardware-accelerated, which means that it should run faster in the C++ implementation.


CLOCKWORK
=========

Clockwork is a 3D software renderer written by myself to understand the
nitty-gritties of what goes into rendering 3D objects, from raw input
data to pixels on a screen.

This is a work in progress so a few bugs may run wild until they get
ironed out, although I assure you that none of them will attempt to find the
answer to life, launch nuclear ABMs or, more realistically, destroy the
content of your hard drive.

Check out the software documentation for a detailed description of the application's
features and their implementations, as well as what you can expect.


Execution
---------
To compile and run this software, you will require a __Java Development Kit__ (JDK) 
and __Apache Ant__ installed on the target computer. Simply launch __ant__
without any arguments and the provided build script will take care of the rest.


Layout
------
The folders provided with this software are structured in the following manner
* __assets__ contains icons and sample models.
* __doc__ contains research, design and implementation documentation, as well as the Javadoc.
* __lib__ contains third-party libraries that this application depends on.
* __src__ contains the source code tree.
* __tst__ contains unit tests.
