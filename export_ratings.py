import pickle
import random

doctor_ratings = [ random.randint(1, 5) for _ in range(500) ]
print( len( doctor_ratings ) )

with open( "ratings.pkl" , "wb" ) as file:
    pickle.dump( doctor_ratings , file )