JAVA SOFTWARE RENDERER
======================

Java Software Renderer (JSR) is a 3D software renderer written by
myself to understand the nitty-gritties of what goes into rendering
3D objects, from their initial raw data to pixels on a screen.

This is a work in progress so a few bugs may run wild until they get
ironed out, although I assure you that none of them will attempt to find the
answer to life, launch nuclear ABMs or, more realistically, destroy the
content of your hard drive.

Check out the __jsr.implementation.pdf__ document for a detailed
description of the application's features and their implementations, as
well as what and what not to expect from JSR.


Execution
---------
This software includes a compiled __jsr.jar__ in the dist (distribution) folder
that can be directly executed, provided you have a Java Runtime Environment
installed on the target computer. The source code can also be compiled and
executed like any other java application.


Hierarchy
---------
The folders provided with this software are structured in the following manner:
* __assets__ contains icons, mesh data and textures.
* __bin__ contains compiled bytecode.
* __dist__ contains a JAR file of the application.
* __doc__ contains the software documentation.
* __lib__ contains external libraries that this application depends on.
* __src__ contains the source code tree.
* __tst__ contains unit tests.