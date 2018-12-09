import React from 'react';

const GC = ({ gcs }) => {

  const gcList = gcs.length ? (
    gcs.map(gc => {
      return  (
        <div className="gc" key={gc.id}>
          <div>GC Type: { gc.GCType }, GC Time: { gc.GCTime }, GC id: {gc.Id}</div>
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