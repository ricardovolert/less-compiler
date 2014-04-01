
# Squarespace LESS Compiler

A Java implementation of the LESS CSS preprocessor language.

Current [Less.js][lessjs] compatibility level: 1.3.3

License: [Apache 2.0](LICENSE) ([tl;dr][license-tldr])

Copyright (c) 2014 SQUARESPACE, Inc.

## Project Goals

 * Migrate away from use of [Node.js][nodejs] + [Less.js][lessjs] for server-side
conversion of LESS to CSS.
 * Maintain compatibility with [Less.js][lessjs], tracking version 1.3.3
   (Squarespace's supported version at the time of development).
 * Improve performance, reduce memory usage where possible.
 * Design must support additional features needed for server-side compilation.
 * Design parsing package to closely track the [Less.js][lessjs] parser
   structure, to simplify verifying correctness.

## Features

 * Performance improvement of 2-5x over Less.js
 * Improved error messages with [full stack traces](docs/error-stack-trace.md)
 * [Execution trace mode](docs/execution-trace.md)
 * Modular parser that supports fine-grained unit testing of syntax fragments
 * High test coverage.

## Differences

 * JavaScript evaluation support is missing.  It may return if / when we can
   guarantee speed and safety of JS evaluation in the JVM (Java 8's Nashorn may
   provide this).
 * Final CSS structure is generated by feeding blocks and rules to a simple
   model that ensures they are emitted at the correct scope in the output
   document.
 * Color keywords can participate in math operations.


[lessjs]: http://lesscss.org/  "Less.js"
[nodejs]: http://nodejs.org/ "Node.js"
[license-tldr]: https://tldrlegal.com/license/apache-license-2.0-(apache-2.0) "Apache 2.0 tl;dr"

