import pickle
import random
import numpy as np
import pandas as pd

names = pickle.load( open( "names.pkl" , "rb" ) )
locations = pickle.load( open( "locations.pkl" , "rb" ) )
ratings = pickle.load( open( "ratings.pkl" , "rb" ) )
specialities = pickle.load( open( "specialities.pkl" , "rb" ) )
degrees = pickle.load( open( "degrees.pkl" , "rb" ) )

for _ in range( 91 ):
    locations.append( random.choice( locations ) )
locations = np.array( locations )

print( len( locations ) )
print( len( names ) )
print( len( ratings ) )
print( len( specialities ) )
print( len( degrees ) )

df_dict = {
    "name" : names ,
    "latitude" : locations[ : , 0 ] ,
    "longitude" : locations[ : , 1 ] ,
    "speciality" : specialities ,
    "degree" : degrees ,
    "rating" : ratings
}

df = pd.DataFrame.from_dict( df_dict )
df.to_csv( "doctors_dataset.csv" , index=False )
