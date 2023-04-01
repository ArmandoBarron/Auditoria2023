#!/bin/bash
dd bs=1024 count=1024000 </dev/urandom >1000MB
dd bs=1024 count=102400 </dev/urandom >100MB
dd bs=1024 count=204800 </dev/urandom >200MB
dd bs=1024 count=10240 </dev/urandom >10MB
dd bs=1024 count=1024 </dev/urandom >1MB