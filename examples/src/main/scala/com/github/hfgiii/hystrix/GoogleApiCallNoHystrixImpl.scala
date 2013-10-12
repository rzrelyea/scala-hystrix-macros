package com.github.hfgiii.hystrix

class GoogleApiCallNoHystrixImpl extends GoogleApiCall{
  override def getMountEverestHight(): String = {
    "0 meters"
  }
  
  override def getDataFromLongRunningOperation(): String = {
    "took 0 seconds"
  }
}