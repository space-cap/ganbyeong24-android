package com.ezlevup.ganbyeong24.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.File

/**
 * 사진 선택 컴포넌트
 *
 * 갤러리에서 사진을 선택하거나 카메라로 촬영할 수 있는 컴포넌트입니다.
 *
 * @param photoUri 선택된 사진의 Uri
 * @param onPhotoSelected 사진 선택 시 호출되는 콜백
 * @param modifier Modifier
 */
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PhotoPickerField(
        photoUri: Uri?,
        onPhotoSelected: (Uri) -> Unit,
        modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // 카메라 권한
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    // 갤러리 선택 런처
    val galleryLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
                    uri: Uri? ->
                uri?.let { onPhotoSelected(it) }
            }

    // 카메라 촬영 런처
    val cameraLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
                    success ->
                if (success && tempPhotoUri != null) {
                    onPhotoSelected(tempPhotoUri!!)
                }
            }

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        // 원형 프로필 이미지 영역
        Box(
                modifier =
                        Modifier.size(120.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(
                                        BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                                        CircleShape
                                )
                                .clickable { showBottomSheet = true },
                contentAlignment = Alignment.Center
        ) {
            if (photoUri != null) {
                // 선택된 사진 표시
                Image(
                        painter = rememberAsyncImagePainter(photoUri),
                        contentDescription = "간병사 사진",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                )
            } else {
                // 기본 아이콘
                Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "사진 추가",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 사진 선택 버튼
        TextButton(onClick = { showBottomSheet = true }) {
            Text(
                    text = if (photoUri != null) "사진 변경" else "사진 선택 (선택사항)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
            )
        }
    }

    // 바텀시트
    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                Text(
                        text = "사진 선택",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // 카메라로 촬영
                Row(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .clickable {
                                            showBottomSheet = false
                                            if (cameraPermissionState.status.isGranted) {
                                                // 임시 파일 생성
                                                val photoFile =
                                                        File.createTempFile(
                                                                "caregiver_photo_",
                                                                ".jpg",
                                                                context.cacheDir
                                                        )
                                                tempPhotoUri =
                                                        FileProvider.getUriForFile(
                                                                context,
                                                                "${context.packageName}.fileprovider",
                                                                photoFile
                                                        )
                                                cameraLauncher.launch(tempPhotoUri!!)
                                            } else {
                                                cameraPermissionState.launchPermissionRequest()
                                            }
                                        }
                                        .padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "카메라",
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "카메라로 촬영", fontSize = 18.sp)
                }

                // 갤러리에서 선택
                Row(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .clickable {
                                            showBottomSheet = false
                                            galleryLauncher.launch("image/*")
                                        }
                                        .padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = "갤러리",
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "갤러리에서 선택", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // 카메라 권한 거부 시 설명 다이얼로그
    if (cameraPermissionState.status.shouldShowRationale) {
        AlertDialog(
                onDismissRequest = {},
                title = { Text("카메라 권한 필요", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                text = { Text("사진을 촬영하려면 카메라 권한이 필요합니다.", fontSize = 18.sp) },
                confirmButton = {
                    TextButton(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                        Text("권한 허용", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = { TextButton(onClick = {}) { Text("취소", fontSize = 18.sp) } }
        )
    }
}
