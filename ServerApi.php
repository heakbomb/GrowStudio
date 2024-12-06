{\rtf1\ansi\ansicpg949\cocoartf2639
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;\f1\fnil\fcharset129 AppleSDGothicNeo-Regular;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww28600\viewh15280\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 <?php\
\
// MySQL 
\f1 \'b5\'a5\'c0\'cc\'c5\'cd\'ba\'a3\'c0\'cc\'bd\'ba
\f0  
\f1 \'bf\'ac\'b0\'e1
\f0  
\f1 \'bc\'b3\'c1\'a4
\f0 \
$servername = "localhost";\
$username = "AndroidUser";\
$password = \'93
\f1 1q2w3e4r
\f0 ;\
$dbname = \'93GPT\'94;\
\
// 
\f1 \'b5\'a5\'c0\'cc\'c5\'cd\'ba\'a3\'c0\'cc\'bd\'ba
\f0  
\f1 \'bf\'ac\'b0\'e1
\f0  
\f1 \'bb\'fd\'bc\'ba
\f0 \
$conn = new mysqli($servername, $username, $password, $dbname);\
\
// 
\f1 \'bf\'ac\'b0\'e1
\f0  
\f1 \'c8\'ae\'c0\'ce
\f0 \
if ($conn->connect_error) \{\
    die("Connection failed: " . $conn->connect_error);\
\}\
\
// API 
\f1 \'b1\'e2\'b4\'c9
\f0  
\f1 \'b0\'e1\'c1\'a4
\f0  (GET, POST 
\f1 \'b5\'ee\'c0\'bb
\f0  
\f1 \'bb\'e7\'bf\'eb\'c7\'cf\'bf\'a9
\f0  
\f1 \'c1\'a4\'ba\'b8
\f0  
\f1 \'c0\'fa\'c0\'e5
\f0  
\f1 \'b9\'d7
\f0  
\f1 \'c1\'b6\'c8\'b8
\f0 )\
$action = isset($_GET['action']) ? $_GET['action'] : "";\
\
switch ($action) \{\
    case 'store':\
        storeUserData($conn);\
        break;\
    case 'get':\
        getUserData($conn);\
        break;\
    default:\
        echo json_encode(["message" => "Invalid action"]);\
        break;\
\}\
\
// 
\f1 \'bb\'e7\'bf\'eb\'c0\'da
\f0  
\f1 \'b5\'a5\'c0\'cc\'c5\'cd\'b8\'a6
\f0  
\f1 \'c0\'fa\'c0\'e5\'c7\'cf\'b4\'c2
\f0  
\f1 \'c7\'d4\'bc\'f6
\f0 \
function storeUserData($conn)\
\{\
    if ($_SERVER['REQUEST_METHOD'] === 'POST') \{\
        // 
\f1 \'bf\'e4\'c3\'bb
\f0  
\f1 \'ba\'bb\'b9\'ae\'bf\'a1\'bc\'ad
\f0  
\f1 \'b5\'a5\'c0\'cc\'c5\'cd
\f0  
\f1 \'b0\'a1\'c1\'ae\'bf\'c0\'b1\'e2
\f0 \
        $username = isset($_POST[\'91AndroidUser\'92]) ? $_POST['AndroidUser'] : "";\
        $email = isset($_POST['email']) ? $_POST['email'] : "";\
        $password = isset($_POST['password']) ? password_hash($_POST['password'], PASSWORD_DEFAULT) : "";\
\
        if ($AndroidUser && $email && $password) \{\
            $sql = "INSERT INTO Users (username, email, password_hash) VALUES (?, ?, ?)";\
            $stmt = $conn->prepare($sql);\
            $stmt->bind_param("sss", $AndroidUser, $email, $password);\
\
            if ($stmt->execute()) \{\
                echo json_encode(["message" => "User saved successfully"]);\
            \} else \{\
                echo json_encode(["error" => "Error: " . $stmt->error]);\
            \}\
\
            $stmt->close();\
        \} else \{\
            echo json_encode(["error" => "Missing required fields"]);\
        \}\
    \} else \{\
        echo json_encode(["error" => "Invalid request method"]);\
    \}\
\}\
\
// 
\f1 \'bb\'e7\'bf\'eb\'c0\'da
\f0  
\f1 \'b5\'a5\'c0\'cc\'c5\'cd\'b8\'a6
\f0  
\f1 \'c1\'b6\'c8\'b8\'c7\'cf\'b4\'c2
\f0  
\f1 \'c7\'d4\'bc\'f6
\f0 \
function getUserData($conn)\
\{\
    if ($_SERVER['REQUEST_METHOD'] === 'GET') \{\
        $user_id = isset($_GET['user_id']) ? intval($_GET['user_id']) : 0;\
\
        if ($user_id > 0) \{\
            $sql = "SELECT user_id, username, email FROM Users WHERE user_id = ?";\
            $stmt = $conn->prepare($sql);\
            $stmt->bind_param("i", $user_id);\
\
            if ($stmt->execute()) \{\
                $result = $stmt->get_result();\
                if ($result->num_rows > 0) \{\
                    $user = $result->fetch_assoc();\
                    echo json_encode($user);\
                \} else \{\
                    echo json_encode(["error" => "User not found"]);\
                \}\
            \} else \{\
                echo json_encode(["error" => "Error: " . $stmt->error]);\
            \}\
\
            $stmt->close();\
        \} else \{\
            echo json_encode(["error" => "Invalid user ID"]);\
        \}\
    \} else \{\
        echo json_encode(["error" => "Invalid request method"]);\
    \}\
\}\
\
// 
\f1 \'bf\'ac\'b0\'e1
\f0  
\f1 \'c1\'be\'b7\'e1
\f0 \
$conn->close();\
?>\
}