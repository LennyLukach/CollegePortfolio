from cmath import pi
import numpy as np
import math
import os

# plot angular velocity / time

#? I = rotaional inertia / I = L/W ### kg * m^2
#? L = angular momentum/ L = mass * velocity * radius ### kgm^2/sec
#? W = anuglar velocity / W = deltaTheta/deltaTime ### rad/sec
#? deltaTheta = angular rotation ### sec

#wf = wi + alpha(t)
#L=mvr
#T =  I(alpha)
#T = Frsin(theta)
#T = Fr
#Fr = I(alpha)
#alpha = Fr/if = mgr/
#I = 2mr^2/2
    

angularRotation = np.rad2deg(2 * pi) #! deg
deltaTime = 2 #! sec
mass = 2 #! kg
radius = 2 #! meters
Wi = 0 #! m/s
gravity = 9.8

vals = []

#? velcity over time for a 
for x in range(10):
    I = (2 * mass * pow(radius, 2))/2
    alpha = mass * gravity * radius
    T = I * alpha
    Wf = round(Wi + alpha * deltaTime, 2)
    Wi = Wf
    vals.append(Wf)

print(vals)

#print(f"{round(rotationalInertia