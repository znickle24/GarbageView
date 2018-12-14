import React from 'react';

const GC = ({ gcs }) => {

  const gcList = gcs.length ? (
    gcs.map(gc => {
      return  (
        <div className="gc" key={gc.id}>
          <div> GC id: {gc.Id}, GC Type: { gc.GCType }, GC Time: { gc.GCTime }, GC Name: {gc.GCName}, GC Cause: {gc.GCCause}, GC Overhead: {gc.GCOverhead}</div>
        {/* <div> GC id: {gc.Id}, GC Type: { gc.GCType }, GC Time: { gc.GCTime }, GC Name: {gc.GCName}, GC Cause: {gc.GCCause}, GC dbMUBGc {gc.dbMUBGc}, GC dbMUAGc: {gc.dbMUAGc}, GC Overhead: {gc.GCOverhead}</div> */}
        </div>
      )
    })
  ) : (
    <p className="center">You have no GC events logged</p>
  )
  
  return(
    <div className="gc-list">
      {gcList}
    </div>
  )
}

export default GC;