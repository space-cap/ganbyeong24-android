package com.ezlevup.ganbyeong24.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 * 이미지 처리 유틸리티
 *
 * 이미지 리사이징, 압축, Base64 인코딩/디코딩 기능을 제공합니다.
 */
object ImageUtils {

    /**
     * Uri를 Base64 문자열로 변환합니다.
     *
     * 이미지를 300x300px로 리사이즈하고 JPEG 압축(품질 80%)을 적용하여 Firestore 문서 크기 제한(1MB) 내에서 저장할 수 있도록 최적화합니다.
     *
     * @param context Android Context
     * @param uri 이미지 Uri
     * @return Base64 인코딩된 문자열, 실패 시 null
     */
    fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (bitmap == null) {
                return null
            }

            // 이미지 회전 정보 확인 및 적용
            val rotatedBitmap = rotateImageIfRequired(context, bitmap, uri)

            // 리사이즈 및 압축
            val optimizedBitmap = resizeAndCompressImage(rotatedBitmap, 300, 300)

            // Base64 인코딩
            bitmapToBase64(optimizedBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Base64 문자열을 Bitmap으로 변환합니다.
     *
     * @param base64String Base64 인코딩된 문자열
     * @return Bitmap 객체, 실패 시 null
     */
    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Bitmap을 리사이즈하고 압축합니다.
     *
     * @param bitmap 원본 Bitmap
     * @param maxWidth 최대 너비
     * @param maxHeight 최대 높이
     * @return 리사이즈된 Bitmap
     */
    private fun resizeAndCompressImage(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        // 비율 계산
        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

        var finalWidth = maxWidth
        var finalHeight = maxHeight

        if (ratioMax > ratioBitmap) {
            finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
        } else {
            finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
        }

        // 리사이즈
        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true)
    }

    /**
     * Bitmap을 Base64 문자열로 변환합니다.
     *
     * @param bitmap Bitmap 객체
     * @param quality JPEG 압축 품질 (0-100)
     * @return Base64 인코딩된 문자열
     */
    private fun bitmapToBase64(bitmap: Bitmap, quality: Int = 80): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    /**
     * EXIF 정보를 확인하여 이미지를 올바른 방향으로 회전합니다.
     *
     * @param context Android Context
     * @param bitmap 원본 Bitmap
     * @param uri 이미지 Uri
     * @return 회전된 Bitmap
     */
    private fun rotateImageIfRequired(context: Context, bitmap: Bitmap, uri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return bitmap

        val exif = ExifInterface(inputStream)
        val orientation =
                exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL
                )

        inputStream.close()

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270f)
            else -> bitmap
        }
    }

    /**
     * Bitmap을 지정된 각도로 회전합니다.
     *
     * @param bitmap 원본 Bitmap
     * @param degrees 회전 각도
     * @return 회전된 Bitmap
     */
    private fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
