from dis import dis
import math
from re import A
from turtle import distance
import matplotlib.pyplot as plt
import numpy as np
import os

'''
How to get an A+:

Cylinder, hollow cylinder
Rotational inertia
Rotational dynamics

'''

def clearScr():
    sysName = os.name
    if sysName == 'posix':
        os.system("clear")
    elif sysName == 'nt':
        os.system("cls")

clearScr()

sind = lambda degrees: (np.sin(np.deg2rad(degrees)))
cosd = lambda degrees: (np.cos(np.deg2rad(degrees)))

ball_distances = []
ball_heights = []
pointSpots = []
speedX = []
speedY = []
ball_ke = []
ball_mgh = []

launch_angle = 45
Vi = 15
accely = -9.8

Viy = (Vi * sind(launch_angle))
Vfy = -Viy
Vix = round(Vi * cosd(launch_angle), 2)
delta_V = Vfy - Viy

time = round(delta_V/accely, 2)
time_increment = time/10


'''Viy = Vi * sind(launch_angle)
Vfy = -Viy
accely = -9.8
delta_V = Vfy - Viy
time = round(delta_V/accely, 2)

Vix = Vi * cosd(launch_angle)
Sx = round(Vix * time, 2)'''


for x in range(11):
    Sx = round(Vix * time_increment * (x), 2)
    Sy = round(Viy * time_increment * (x) + (1/2) * accely * pow((time_increment * (x)), 2), 2)
    ball_distances.append(Sx)
    ball_heights.append(Sy)
    Vfy = round(Viy + accely * time_increment * (x), 2)
    speedX.append(Vix)
    speedY.append(Vfy)
    Vf = math.sqrt(pow(Vfy, 2) + pow(Vix, 2))
    ke = round(1/2 * pow(Vfy, 2), 1)
    ball_ke.append(ke)
    mgh = round(accely * -1 * Sy, 1)
    ball_mgh.append(mgh)

ball_heights[-1] = 0.00


fig, ax = plt.subplots()

points = ax.scatter(ball_distances, ball_heights, color="orange")
ax.plot(ball_distances, ball_heights)

ax.set_title("Ball Trajectory Graph")
ax.set_xlabel("Distance")
ax.set_ylabel("Height")

for point in range(11):
    pointSpots.append((points.get_offsets().data[point][0], points.get_offsets().data[point][1]))

count = 0
for point in pointSpots:
    plt.annotate(f"   Vx:{speedX[count]}m/s\n   Vy:{speedY[count]}m/s\n   X:{point[0]}m\n   Y:{point[1]}m\n   KE:{ball_ke[count]}J\n   MGH:{ball_mgh[count]}J", point, horizontalalignment='left', verticalalignment='top', fontsize=9) 
    count += 1



#print(f"SpeedX: {speedX}")
#print(f"SpeedY: {speedY}")
#print(f"Energy: {ball_energy}")
#print(f"Distance: {ball_distances}")
#print(f"Height: {ball_heights}")


#? I = rotaional inertia / I = L/W ### kg * m^2
#? L = angular momentum/ L = mass * velocity * radius ### kgm^2/sec
#? W = anuglar velocity / W = deltaTheta/deltaTime ### rad/sec
#? deltaTheta = angular rotation ### sec

angularRotation = np.rad2deg(2 * math.pi) #! deg
deltaTime = 2 #! sec
mass = 2 #! kg
radius = 2 #! meters
Wi = 0 #! m/s
gravity = 9.8

vals = []
timeIncrements = []
time_increment2 = deltaTime/10

#? velcity over time for a disc
for x in range(10):
    alpha = (2 * gravity) / radius
    Wf = round(Wi + alpha * deltaTime, 2)
    Wi = Wf
    vals.append(Wf)
    timeIncrements.append(time_increment2 * x)



#print(f"{round(rotationalInertia, 2)}k*m^2")


plt.figure()
plt.plot(timeIncrements, vals, color="lightgreen")
plt.title("Angular Velocity over time for a disc")
plt.ylabel("Angular Velocity")
plt.xlabel("Time")

angularRotation = np.rad2deg(2 * math.pi) #! deg
deltaTime = 2 #! sec
mass = 2 #! kg
radius = 2 #! meters
Wi = 0 #! m/s
gravity = 9.8

vals = []
timeIncrements = []
time_increment2 = deltaTime/10

#? velcity over time for a spherical shell
for x in range(10):
    alpha = (2/3 * mass * pow(radius, 2))
    Wf = round(Wi + alpha * deltaTime, 2)
    Wi = Wf
    vals.append(Wf)
    timeIncrements.append(time_increment2 * x)

plt.figure()
plt.plot(timeIncrements, vals, color="yellow")
plt.title("Angular Velocity for a spherical shell")
plt.xlabel("Time")
plt.ylabel("Angular Velocity")

plt.show()