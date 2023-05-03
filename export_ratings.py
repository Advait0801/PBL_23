import pickle
import random

cdf = [ 0.1 , 0.2 , 0.4 , 0.9 , 1.0 ]

def get_rating():
    alpha = random.uniform( 0 , 1 )
    for i in range( 5 ):
        if alpha < cdf[i]:
            return i + 1

doctor_ratings = [ get_rating() for _ in range( 500 ) ]

with open( "ratings.pkl" , "wb" ) as file:
    pickle.dump( doctor_ratings , file )