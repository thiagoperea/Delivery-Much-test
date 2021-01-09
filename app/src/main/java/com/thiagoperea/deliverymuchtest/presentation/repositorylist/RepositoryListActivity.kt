package com.thiagoperea.deliverymuchtest.presentation.repositorylist

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.thiagoperea.deliverymuchtest.R
import com.thiagoperea.deliverymuchtest.data.model.Repository
import org.koin.android.viewmodel.ext.android.viewModel

class RepositoryListActivity : AppCompatActivity() {

    private val viewModel by viewModel<RepositoryListViewModel>()

    private var adapter: RepositoryListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        setupToolbar()
        setupSearchBar()
        setupList()
        setupObservers()
    }

    private fun setupList() {
        this.adapter = RepositoryListAdapter(::onRepositoryClick)

        findViewById<RecyclerView>(R.id.repositoryListView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RepositoryListActivity)
            adapter = this@RepositoryListActivity.adapter
            addItemDecoration(DividerItemDecoration(this@RepositoryListActivity, VERTICAL))
        }
    }

    private fun onRepositoryClick(repository: Repository) {
        toast("Show deteails of: ${repository.name} from ${repository.author}")
    }

    private fun setupObservers() {
        viewModel.searchState.observe(this) {
            when (it) {
                is RepositoryListState.Loading -> onSearchLoading()
                is RepositoryListState.Success -> onSearchSuccess(it.repositories)
                is RepositoryListState.Error -> onSearchError(it.errorMessage)
            }
        }
    }

    private fun onSearchError(errorMessage: String) {
        hideLoading()
        toast(errorMessage)
    }

    private fun onSearchSuccess(repositories: List<Repository>) {
        hideLoading()
        adapter?.setData(repositories)
    }

    private fun onSearchLoading() {
        adapter?.clearData()
        showLoading()
    }

    private fun showLoading() {
        findViewById<ProgressBar>(R.id.repositoryListLoading).visibility = View.VISIBLE
        findViewById<RecyclerView>(R.id.repositoryListView).visibility = View.GONE
    }

    private fun hideLoading() {
        findViewById<ProgressBar>(R.id.repositoryListLoading).visibility = View.GONE
        findViewById<RecyclerView>(R.id.repositoryListView).visibility = View.VISIBLE
    }

    private fun setupSearchBar() {
        findViewById<TextInputLayout>(R.id.repositoryListSearchField).apply {
            setEndIconOnClickListener {
                viewModel.doSearch(this.editText?.text.toString())
            }

            editText?.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.doSearch(this.editText?.text.toString())
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }

    }

    private fun setupToolbar() {
        findViewById<MaterialToolbar>(R.id.repositoryListToolbar)?.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.dayNightMenu) {
                //TODO: ViewModel.switchDayNight
                viewModel.switchDayNight()
                toast("mudar tema")
            }
            return@setOnMenuItemClickListener true
        }
    }
}

fun AppCompatActivity.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()