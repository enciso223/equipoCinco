package com.univalle.equipocinco.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.univalle.equipocinco.R
import com.univalle.equipocinco.databinding.FragmentLoginBinding
import com.univalle.equipocinco.util.SessionManager


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        // Check if already logged in
        if (sessionManager.isLoggedIn()) {
            navigateToHome()
            return
        }

        setupBiometric()
        setupUI()
    }

    private fun setupUI() {
        // Lottie animation click listener
        binding.lottieFingerprint.setOnClickListener {
            if (checkBiometricSupport()) {
                biometricPrompt.authenticate(promptInfo)
            }
        }
    }

    private fun setupBiometric() {
        val executor = ContextCompat.getMainExecutor(requireContext())

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        requireContext(),
                        "Error de autenticación: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    sessionManager.setLoggedIn(true)
                    navigateToHome()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        requireContext(),
                        "Huella no reconocida",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación con Biometría")
            .setSubtitle("Ingrese su huella digital")
            .setNegativeButtonText("Cancelar")
            .build()
    }

    private fun checkBiometricSupport(): Boolean {
        val biometricManager = BiometricManager.from(requireContext())
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> return true
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(requireContext(), "No hay sensor biométrico", Toast.LENGTH_SHORT).show()
                return false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(requireContext(), "Sensor no disponible", Toast.LENGTH_SHORT).show()
                return false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(requireContext(), "No hay huellas registradas", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return false
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}